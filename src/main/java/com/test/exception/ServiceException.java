package com.test.exception;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/10/24
 * Email: xionggao@terminus.io
 */
public class ServiceException extends RuntimeException{
    
    private static final long serialVersionUID = -6468897270979367613L;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
