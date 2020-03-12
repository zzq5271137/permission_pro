package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.service.IEmployeeService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response) {
        HSSFWorkbook workbook = employeeService.downloadExcel();
        String fileName = new String("员工数据表.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("content-Disposition", "attachment;filename=" + fileName);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
