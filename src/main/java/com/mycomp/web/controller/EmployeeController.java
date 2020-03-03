package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Employee;
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

    @RequestMapping("/saveEmployee")
    @ResponseBody
    public AjaxRes saveEmployee(Employee employee) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employee.setState(true);
            employeeService.saveEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("保存成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败...");
        }
        return ajaxRes;
    }

    @RequestMapping("/updateEmployee")
    @ResponseBody
    public AjaxRes updateEmployee(Employee employee) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("更新成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败...");
        }
        return ajaxRes;
    }

    @RequestMapping("/softDeleteEmployee")
    @ResponseBody
    public AjaxRes softDeleteEmployee(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.softDeleteEmployee(id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("已将该员工设置为离职状态！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败...");
        }
        return ajaxRes;
    }
}
