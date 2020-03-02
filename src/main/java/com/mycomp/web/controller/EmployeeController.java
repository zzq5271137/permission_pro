package com.mycomp.web.controller;

import com.mycomp.domain.PageListRes;
import com.mycomp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeController {

    // 注入service层对象
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/employee")
    public String employee() {
        return "employee";
    }

    @RequestMapping("/employeelist")
    @ResponseBody  // 返回json数据
    public PageListRes employeelist() {
        return employeeService.getAllEmployees();
    }

}
