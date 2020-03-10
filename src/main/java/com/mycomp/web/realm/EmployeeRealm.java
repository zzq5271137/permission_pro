package com.mycomp.web.realm;

import com.mycomp.domain.Employee;
import com.mycomp.service.IEmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeRealm extends AuthorizingRealm {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 认证;
     * Shiro会自动地将登录页面的表单中的username和password封装成Token对象, 并传入此方法中;
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 获取身份信息
        String username = (String) token.getPrincipal();
        System.out.println("用户`" + username + "`登录认证中...");

        // 到数据库中查询有无当前用户
        Employee employee = employeeService.getEmployeeByUsername(username);
        if (employee == null) {
            return null;
        }

        /*
         * 交由认证器去做认证;
         * 参数: 1. 主体(当前用户), 传入主体的目的是, Shiro会自动地把当前用户放入Shiro管理的session中;
         *      2. 正确的凭证(从数据库中查出的);
         *      3. 盐(salt);
         *      4. 当前Realm的名字;
         *
         * 想要知道认证成功或者认证失败, 需要定义一个filter, 详见LoginFormFilter.java
         */
        return new SimpleAuthenticationInfo(
                employee,
                employee.getPassword(),
                this.getName());
    }

    /**
     * 授权;
     * 什么时候调用此方法:
     * 1. 当发现访问路径对应的处理器方法上面有授权的注解, 就会调用此方法;
     * 2. 当页面当中有授权的标签(如shiro:hasPermission、shiro:hasRole等), 也会调用此方法;
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

}
