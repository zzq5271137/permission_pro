package com.mycomp.service;

import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;

import java.util.List;

public interface IEmployeeService {

    PageListRes getAllEmployees(EmployeelistQueryVo queryVo);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void softDeleteEmployee(Long id);

    Employee getEmployeeByUsername(String username);

    List<String> getRolesByEmployeeId(Long id);

    List<String> getPermissionsByEmployeeId(Long id);
}
