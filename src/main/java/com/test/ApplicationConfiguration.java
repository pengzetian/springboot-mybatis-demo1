package com.test;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.Executors;


@EnableWebMvc
@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.test"})
public class ApplicationConfiguration {
    
    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

}
