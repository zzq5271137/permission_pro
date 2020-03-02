package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.Employee;
import com.mycomp.domain.PageListRes;
import com.mycomp.mapper.EmployeeMapper;
import com.mycomp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    // 注入dao层对象
    @Autowired
    private EmployeeMapper employeeMapper;

    /*
     * 查询所有employee
     */
    @Override
    public PageListRes getAllEmployees() {
        // 设置分页
        Page<Object> page = PageHelper.startPage(1, 5);

        // 从dao层查询所有employee
        List<Employee> employees = employeeMapper.selectAll();

        // 封装成PageListRes对象
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);

        return pageListRes;
    }

}
