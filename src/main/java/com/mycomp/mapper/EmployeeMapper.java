package com.mycomp.mapper;

import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectAll(EmployeelistQueryVo queryVo);

    void insert(Employee record);

    void updateByPrimaryKey(Employee record);

    void softDeleteByPrimaryKey(Long id);

    Employee selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);
}