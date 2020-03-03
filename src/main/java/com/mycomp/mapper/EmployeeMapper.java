package com.mycomp.mapper;

import com.mycomp.domain.Employee;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectAll();

    void insert(Employee record);

    void updateByPrimaryKey(Employee record);

    void softDeleteByPrimaryKey(Long id);

    Employee selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);
}