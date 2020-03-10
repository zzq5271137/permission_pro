package com.mycomp.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/*
 * 登录表单认证过滤器;
 * 想要用上此过滤器, 需要在配置文件中进行配置, 详见application-shiro.xml
 */

public class LoginFormFilter extends FormAuthenticationFilter {

    /**
     * 当认证成功时会调用
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        System.out.println("用户`" + token.getPrincipal() + "`认证成功！");

        // 响应给浏览器
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMsg("登录成功！");
        // 需要把对象转成json格式的字符串
        String ajaxResJsonStr = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(ajaxResJsonStr);

        return false;
    }

    /**
     * 当认证失败时会调用
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        System.out.println("用户`" + token.getPrincipal() + "`认证失败...");

        // 响应给浏览器
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        if (e != null) {
            String exceptionName = e.getClass().getName();
            if (exceptionName.equals(UnknownAccountException.class.getName())) {
                // 没有该账号
                ajaxRes.setMsg("没有该账号...");
            } else if (exceptionName.equals(IncorrectCredentialsException.class.getName())) {
                // 密码不正确
                ajaxRes.setMsg("密码与账号不匹配...");
            } else {
                // 未知异常
                ajaxRes.setMsg("未知错误, 请重试...");
            }
        }
        try {
            // 需要把对象转成json格式的字符串
            String ajaxResJsonStr = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(ajaxResJsonStr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
