package com.mycomp.interceptor;

import com.mycomp.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 自定义拦截器, 在application-mvc.xml中进行配置
 */

public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 通过本地线程变量(ThreadLocal)来存取request对象, 详见RequestUtil.java
        RequestUtil.setRequest(request);
        return true;
    }

}
