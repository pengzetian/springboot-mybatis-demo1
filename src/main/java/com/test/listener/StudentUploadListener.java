package com.test.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.test.event.StudentUploadEvent;
import com.test.logic.StudentLogic;
import com.test.model.Student;
import com.test.model.TaskResult;
import com.test.utils.ImportTaskRedisHandler;
import com.test.utils.TaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@Component
public class StudentUploadListener {
    
    @Autowired
    private ImportTaskRedisHandler importTaskRedisHandler;
    
    @Autowired
    private EventBus eventBus;
    
    @Autowired
    private StudentLogic studentLogic;
    
    
    @PostConstruct
    public void init() {
        this.eventBus.register(this);
    }
    
    
    @Subscribe
    public void upload(StudentUploadEvent studentUploadEvent) {
        new TaskExecutor<Student>(studentUploadEvent.getTaskId(), studentUploadEvent.getStudents(), importTaskRedisHandler) {
            @Override
            protected TaskResult executeMethod(Student student) {
                return studentLogic.createOrUpdateStudent(student);
            }
        }.execute();
    }
    
}
