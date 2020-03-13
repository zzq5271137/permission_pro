package com.mycomp.aspect;

/*
 * 系统日志的切面类
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.domain.Systemlog;
import com.mycomp.mapper.SystemlogMapper;
import com.mycomp.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Transactional
public class SystemlogAspect {

    @Autowired
    private SystemlogMapper systemlogMapper;

    @After(value = "SystemlogAspect.servicePoint()")
    public void writeLog(JoinPoint joinPoint) throws JsonProcessingException {
        System.out.println("记录系统日志...");
        Systemlog systemlog = new Systemlog();

        // 记录时间
        systemlog.setOptime(new Date());

        // 记录用户的ip地址(添加一个拦截器, 获取请求对象, 详见RequestInterceptor.java)
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            String ip = request.getRemoteAddr();
            systemlog.setIp(ip);
        }

        // 记录当前执行的方法(通过传入的JoinPoint获取)
        String methodClassPath = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String func = methodClassPath + ":" + methodName;
        systemlog.setFunc(func);

        // 记录方法参数(转成json格式)
        if (!"uploadEmpExcel".equals(methodName)
                && !"resolveExcel".equals(methodName)
                && !"getCellValue".equals(methodName)) {
            String params = new ObjectMapper().writeValueAsString(joinPoint.getArgs());
            systemlog.setParams(params);
        }

        System.out.println("系统日志生成完毕: " + systemlog);

        try {
            systemlogMapper.insert(systemlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Pointcut(value = "execution(* com.mycomp.service.*.*(..))")
    private void servicePoint() {
    }

}
