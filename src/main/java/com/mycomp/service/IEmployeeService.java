package com.mycomp.service;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService {

    PageListRes getAllEmployees(EmployeelistQueryVo queryVo);

    AjaxRes saveEmployee(Employee employee);

    AjaxRes updateEmployee(Employee employee);

    AjaxRes softDeleteEmployee(Long id);

    Employee getEmployeeByUsername(String username);

    List<String> getRolesByEmployeeId(Long id);

    List<String> getPermissionsByEmployeeId(Long id);

    HSSFWorkbook downloadExcel();

    AjaxRes uploadEmpExcel(MultipartFile excel);

}
