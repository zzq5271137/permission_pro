package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.mapper.EmployeeMapper;
import com.mycomp.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

    // 注入dao层对象
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageListRes getAllEmployees(EmployeelistQueryVo queryVo) {
        /*
         * 设置分页:
         * 根据easy-ui的分页器传过来的参数动态地设置分页;
         * 这样做, 会使从数据库查询出来的分页与前端页面上的分页保持一致;
         */
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());

        // 从dao层查询所有employee
        List<Employee> employees = employeeMapper.selectAll(queryVo);

        // 封装成PageListRes对象
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);

        return pageListRes;
    }

    @Override
    public void saveEmployee(Employee employee) {
        // 1. 保存员工
        employeeMapper.insert(employee);

        // 2. 保存员工与角色的关系
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        // 1. 先打破员工与角色原来的关系
        employeeMapper.deleteEmployeeRoleRel(employee.getId());

        // 2. 更新员工信息
        employeeMapper.updateByPrimaryKey(employee);

        // 3. 再重新建立关系
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void softDeleteEmployee(Long id) {
        employeeMapper.softDeleteByPrimaryKey(id);
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeMapper.getEmployeeByUsername(username);
    }

}
