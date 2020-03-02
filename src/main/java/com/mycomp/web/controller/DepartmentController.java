package com.mycomp.web.controller;

import com.mycomp.domain.Department;
import com.mycomp.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("/departmentList")
    @ResponseBody
    public List<Department> departmentList() {
        return departmentService.getAllDepartments();
    }

}
