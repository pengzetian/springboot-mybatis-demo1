package com.test.logic;


import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import com.test.enums.TaskStatusEnum;
import com.test.event.StudentUploadEvent;
import com.test.exception.JsonResponseException;
import com.test.model.Student;
import com.test.model.TaskResult;
import com.test.parser.StudentParser;
import com.test.utils.ImportTask;
import com.test.utils.ImportTaskRedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@Component
public class StudentLogic {
    
    
    private final EventBus eventBus;
    
    private final ImportTaskRedisHandler importTaskRedisHandler;
    
    @Autowired
    public StudentLogic(EventBus eventBus, ImportTaskRedisHandler importTaskRedisHandler) {
        this.eventBus = eventBus;
        this.importTaskRedisHandler = importTaskRedisHandler;
    }
    
    
    public String importStudentInfo(MultipartFile file) {
        try {
            List<Student> students = new StudentParser().parseToObj(file);
            ImportTask importTask = new ImportTask();
            importTask.setStatus(TaskStatusEnum.WAITING);
            String taskId = importTaskRedisHandler.saveTask(importTask);
            eventBus.post(new StudentUploadEvent(taskId, students));
            return taskId;
        } catch (Exception e) {
            log.error("fail parse file with cause={}", Throwables.getStackTraceAsString(e));
            throw new JsonResponseException("excel.parse.fail");
        }
    }
    
    
    public TaskResult createOrUpdateStudent(Student student) {
    
        //do create and update
        return TaskResult.ok();
    }
    
}
