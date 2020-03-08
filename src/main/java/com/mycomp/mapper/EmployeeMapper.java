package com.mycomp.mapper;

import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {

    List<Employee> selectAll(EmployeelistQueryVo queryVo);

    void insert(Employee record);

    void updateByPrimaryKey(Employee record);

    void softDeleteByPrimaryKey(Long id);

    void insertEmployeeRoleRel(@Param("eid") Long eid, @Param("rid") Long rid);

    void deleteEmployeeRoleRel(Long id);

    Employee selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);
}