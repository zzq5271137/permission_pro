package com.mycomp.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    // 通过本地线程变量(ThreadLocal)来存取request对象
    public static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        return currentRequest.get();
    }

    public static void setRequest(HttpServletRequest request) {
        currentRequest.set(request);
    }

}
