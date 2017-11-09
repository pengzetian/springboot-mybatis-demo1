package com.test.utils;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@Slf4j
@Component
public class ImportTaskRedisHandler {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    private final static String BASE_KEY = "IMPORT_TASK";
    
    
    /**
     * 创建Task
     *
     * @param task 任务
     * @return 任务 ID
     */
    public String saveTask(ImportTask task) {
        String value = JsonMapper.nonDefaultMapper().toJson(task);
        String key = key();
        stringRedisTemplate.opsForValue().set(key, value,30 * 60, TimeUnit.SECONDS);
        return key;
    }
    
    /**
     * 更新Task,key的失效时间由调用方决定
     * @param key  任务ID
     * @param task  任务
     * @param expireSeconds 失效时间
     */
    public void updateWithTimeSpecified(String key, ImportTask task, int expireSeconds) {
        String value = JsonMapper.nonDefaultMapper().toJson(task);
        stringRedisTemplate.opsForValue().set(key, value, expireSeconds);
    }
    
    
    /**
     * 获取到 任务
     * @param key 任务ID
     * @return 任务
     */
    public ImportTask getTask(final String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return JsonMapper.nonDefaultMapper().fromJson(value, ImportTask.class);
    }
    
    private String key() {
        return BASE_KEY + ":" + DateTime.now().toDate().getTime();
    }
}
