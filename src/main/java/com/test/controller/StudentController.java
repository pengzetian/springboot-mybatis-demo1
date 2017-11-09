package com.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import com.test.event.TestEvent;
import com.test.exception.JsonResponseException;
import com.test.model.Student;
import com.test.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private EventBus eventBus;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    
    
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
    
    
    /**
     * 测试 下 redis
     */
    @GetMapping("test-redis")
    public Student testJedis() {
       
        Student student = studentService.findById(1L);
        
        String json = convertObjectToString(student);
    
        stringRedisTemplate.opsForValue().set("student", json);
        
        String string = stringRedisTemplate.opsForValue().get("student");
        
        System.out.println("string={}" + string);
        
        return convertStringToObject(json);
    }
    
    
    private Student convertStringToObject(String json) {
        try {
            return objectMapper.readValue(json, Student.class);
        } catch (Exception e) {
            log.error("fail convert String to Object fail cause={}", Throwables.getStackTraceAsString(e));
            throw new JsonResponseException("convert.String.to.Object.fail");
        }
    }
    
    private String convertObjectToString(Student student) {
        try {
            return objectMapper.writeValueAsString(student);
        } catch (Exception e) {
            log.error("convert object to String fail cause={}", Throwables.getStackTraceAsString(e));
            throw new JsonResponseException("convert.Object.to.String.fail");
        }
    }
   
    
    
}
