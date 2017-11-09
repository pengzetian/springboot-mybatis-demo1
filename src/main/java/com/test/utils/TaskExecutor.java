package com.test.utils;

import com.google.common.collect.Lists;
import com.test.enums.TaskStatusEnum;
import com.test.model.TaskResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@Slf4j
public abstract class TaskExecutor<T> {
    
    private final static int IMPORT_EXPIRED_TIME = 120;
    private final ImportTaskRedisHandler importTaskRedisHandler;
    
    private List<T> datas;
    private String taskId;
    
    
    public TaskExecutor(String taskId, List<T> datas, ImportTaskRedisHandler importTaskRedisHandler) {
        this.taskId = taskId;
        this.datas = datas;
        this.importTaskRedisHandler = importTaskRedisHandler;
    }
    
    public void execute() {
        ImportTask task = importTaskRedisHandler.getTask(taskId);
        List<TaskResult> errorResult = Lists.newArrayList();
        List<TaskResult> successResult = Lists.newArrayList();
        int currentNum = 0;
        
        ImportProgress progress = new ImportProgress();
        progress.setFailed(0);
        progress.setSuccess(0);
        progress.setTotal(datas.size());
        progress.setRemain(datas.size());
        task.setStatus(TaskStatusEnum.WAITING);
        task.setProgress(JsonMapper.nonEmptyMapper().toJson(progress));
        importTaskRedisHandler.updateWithTimeSpecified(taskId, task, IMPORT_EXPIRED_TIME);
        
        for(T t : datas) {
            TaskResult result = executeMethod(t);
            if(!result.isSuccess()) {
                errorResult.add(result);
            } else {
                successResult.add(result);
            }
            currentNum ++;
            // 每发送100条就去redis更新一下已发送的状态，商品操作相对会比较慢，所以将更新频率调高
            // 最后一条数据更新，更新数据库数据
            if (currentNum % 100 == 0 || progress.getTotal() == currentNum + 1) {
                progress.setFailed(errorResult.size());
                progress.setSuccess(currentNum - errorResult.size() + 1);
                progress.setRemain(progress.getTotal() - currentNum - 1);
                task.setStatus(TaskStatusEnum.WAITING);
                task.setProgress(JsonMapper.nonEmptyMapper().toJson(progress));
                importTaskRedisHandler.updateWithTimeSpecified(taskId, task, IMPORT_EXPIRED_TIME);
            }
        }
        
        if(errorResult.isEmpty()) {
            log.info("execute success .......");
            task.setStatus(TaskStatusEnum.SUCCESS);
            task.setSuccess(JsonMapper.nonDefaultMapper().toJson(successResult));
            importTaskRedisHandler.updateWithTimeSpecified(taskId, task, IMPORT_EXPIRED_TIME);
        } else {
            log.error("execute failed data = {}", errorResult);
            task.setStatus(TaskStatusEnum.FAILURE);
            task.setError(JsonMapper.nonDefaultMapper().toJson(errorResult));
            importTaskRedisHandler.updateWithTimeSpecified(taskId, task, IMPORT_EXPIRED_TIME);
            
        }
    }
    
    protected abstract TaskResult executeMethod(T t);
    
    
}
