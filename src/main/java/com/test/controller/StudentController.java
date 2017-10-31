package com.test.controller;

import com.google.common.eventbus.EventBus;
import com.test.event.TestEvent;
import com.test.model.Student;
import com.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/10/29
 * Email: xionggao@terminus.io
 */
@RestController
@RequestMapping("/api/user")
public class StudentController {
    
    
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private EventBus eventBus;
    
    
    
    @GetMapping("test")
    public String test() {
        
        return "hello";
    }
    
    @GetMapping("list")
    public List<Student> listStudent() {
        return studentService.list();
    }
    
    
    @GetMapping("/{id}")
    public Student findById(@PathVariable("id") Long id) {
        return studentService.findById(id);
    }
    
    
    /**
     * 测试下 抛出事件
     */
    @GetMapping("/test-event")
    public void testEvent() {
        
        System.out.println("do some login .." + "start");
        
        eventBus.post(TestEvent.builder().id(1L).build());
        System.out.println("post event end....");
    }
    
}
