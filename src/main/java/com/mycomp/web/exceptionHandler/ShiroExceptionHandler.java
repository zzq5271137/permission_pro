package com.mycomp.web.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.domain.AjaxRes;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 处理Shiro异常
 */

@ControllerAdvice
public class ShiroExceptionHandler {

    /**
     * @param handlerMethod 触发异常的方法
     */
    @ExceptionHandler(AuthorizationException.class)
    public void handleAuthorizationException(HandlerMethod handlerMethod, HttpServletResponse response)
            throws IOException {
        /*
         * 有两种情况:
         * 1. 触发异常的请求是通过AJAX发送的, 这时, 就无法在这里进行跳转页面;
         * 2. 触发异常的请求不是通过AJAX发送的, 在这里可以跳转;
         *
         * 所以要判断该请求是不是通过AJAX发送的(是不是json的请求);
         * 如果是json请求, 要返回json数据给浏览器, 让其自己做跳转;
         *
         * 可以通过该请求的处理器方法上有无@ResponseBody注解来判断该请求是否是AJAX请求(传入HandlerMethod对象);
         */
        ResponseBody responseBodyAnnotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (responseBodyAnnotation != null) {
            // 表示此请求是AJAX请求, 所以要返回json数据
            response.setCharacterEncoding("utf-8");
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("你没有权限执行该操作！");
            String ajaxResStr = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(ajaxResStr);
        } else {
            // 表示此请求不是AJAX请求, 可以直接在这里做跳转
            response.sendRedirect("/static/views/errorPages/noPermission.jsp");
        }
    }

}
