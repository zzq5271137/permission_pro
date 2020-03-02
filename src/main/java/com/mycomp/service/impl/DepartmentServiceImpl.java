package com.mycomp.service.impl;

import com.mycomp.domain.Department;
import com.mycomp.mapper.DepartmentMapper;
import com.mycomp.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.selectAll();
    }

}
