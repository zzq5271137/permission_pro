package com.mycomp.mapper;

import com.mycomp.domain.Department;

import java.util.List;

public interface DepartmentMapper {

    List<Department> selectAll();

}