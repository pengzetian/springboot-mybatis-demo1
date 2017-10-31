package com.test.interceptors;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 测试 自定义 拦截器
 */

public class TestInterceptor extends HandlerInterceptorAdapter {
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("do some logic ...");
        }
        System.out.println("session is null ...");
        return true;
    }
    
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //UserUtil.clearCurrentUser();
        System.out.println("do some login ...after completion");
    }
    

}
