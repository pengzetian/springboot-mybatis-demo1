package com.test.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.test.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/10/31
 * Email: xionggao@terminus.io
 */
@Slf4j
@Service
public class TestListener {
    
    @Autowired
    private EventBus eventBus;
    
    
    @PostConstruct
    public void register() {
        eventBus.register(this);
    }
    
    
    @Subscribe
    public void testListener(TestEvent event) {
        Long id = event.getId();
        
        System.out.println("get id={}" + id);
        
    }
    
    
}
