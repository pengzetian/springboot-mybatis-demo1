package com.test.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/11/9
 * Email: xionggao@terminus.io
 */
@ToString
@EqualsAndHashCode
public class TaskResult implements Serializable{
    
    private static final long serialVersionUID = -9095045668658168343L;
    
    public static TaskResult ok() {
        TaskResult result = new TaskResult();
        result.setSuccess(true);
        return result;
    }
    
    public static TaskResult ok(String result) {
        TaskResult taskResult = new TaskResult();
        taskResult.setResult(result);
        taskResult.setSuccess(true);
        return taskResult;
    }
    
    public static TaskResult fail(String key, String message) {
        TaskResult result = new TaskResult();
        result.setSuccess(false);
        result.setKey(key);
        result.setMessage(message);
        return result;
    }
    
    @Setter
    private Boolean success;
    
    public Boolean isSuccess() {
        return this.success;
    }
    
    @Getter
    @Setter
    private String key;
    
    @Getter
    @Setter
    private String message;
    
    @Getter
    @Setter
    private String result;
}
