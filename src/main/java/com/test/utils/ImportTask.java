package com.test.utils;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:  导入任务
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */

import com.test.enums.TaskStatusEnum;
import lombok.Data;

import java.io.Serializable;


@Data
public class ImportTask implements Serializable{
    
    
    private static final long serialVersionUID = -4638750141869362210L;
    
    /**
     * 处理状态
     */
    private TaskStatusEnum status;
    
    /**
     * 错误描述
     */
    private String error;
    
    /**
     * 成功结果描述
     */
    private String success;
    
    /**
     * 导入进度
     */
    private String progress;
    
    
}
