package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.service.IEmployeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {

    // 注入service层对象
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions("employee:index")
    @RequestMapping("/employee")
    public String employee() {
        return "employee";
    }

    @RequestMapping("/employeelist")
    @ResponseBody  // 返回json数据
    public PageListRes employeelist(EmployeelistQueryVo queryVo) {
        return employeeService.getAllEmployees(queryVo);
    }

    @RequiresPermissions("employee:add")
    @RequestMapping("/saveEmployee")
    @ResponseBody
    public AjaxRes saveEmployee(Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequiresPermissions("employee:edit")
    @RequestMapping("/updateEmployee")
    @ResponseBody
    public AjaxRes updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @RequiresPermissions("employee:delete")
    @RequestMapping("/softDeleteEmployee")
    @ResponseBody
    public AjaxRes softDeleteEmployee(Long id) {
        return employeeService.softDeleteEmployee(id);
    }

}
