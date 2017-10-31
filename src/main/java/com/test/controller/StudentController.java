package com.test.controller;

import com.google.common.eventbus.EventBus;
import com.test.event.TestEvent;
import com.test.model.Student;
import com.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    
    /**
     * 测试下 定时器 job
     */
    @Scheduled(cron = "0 */1 * * * ?")
    @RequestMapping(value = "delta", produces = MediaType.APPLICATION_JSON_VALUE)
    public void testScheule() {
        System.out.println(" 一分钟 执行 一次 。。。" );
    }
    
}
