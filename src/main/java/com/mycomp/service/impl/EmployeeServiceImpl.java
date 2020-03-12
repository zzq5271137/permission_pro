package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.*;
import com.mycomp.mapper.EmployeeMapper;
import com.mycomp.service.IEmployeeService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public AjaxRes saveEmployee(Employee employee) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employee.setState(true);

            /*
             * 保存之前, 需要对密码进行加密处理, 加密参数如下:
             * a. 加密算法: md5
             * b: 散列次数: 2
             * c. 盐: 用户名
             *
             * 在Shiro做登录认证时, 也需要加密处理:
             * (1). 在配置文件中配置一个凭证匹配器, 告知其加密算法和散列次数(详见application-shiro.xml);
             * (2). 在自定义Realm中加入盐(详见EmployeeRealm.java);
             */
            Md5Hash pwdMd5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
            employee.setPassword(pwdMd5Hash.toString());

            // 1. 保存员工
            employeeMapper.insert(employee);

            // 2. 保存员工与角色的关系
            for (Role role : employee.getRoles()) {
                employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
            }

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("员工保存成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("员工保存失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes updateEmployee(Employee employee) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            // 1. 先打破员工与角色原来的关系
            employeeMapper.deleteEmployeeRoleRel(employee.getId());

            // 2. 更新员工信息
            employeeMapper.updateByPrimaryKey(employee);

            // 3. 再重新建立关系
            for (Role role : employee.getRoles()) {
                employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
            }

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("员工更新成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("员工更新失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes softDeleteEmployee(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeMapper.softDeleteByPrimaryKey(id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("已将该员工设置为离职状态！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("员工离职失败...");
        }
        return ajaxRes;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeMapper.getEmployeeByUsername(username);
    }

    @Override
    public List<String> getRolesByEmployeeId(Long id) {
        return employeeMapper.getRolesByEmployeeId(id);
    }

    @Override
    public List<String> getPermissionsByEmployeeId(Long id) {
        return employeeMapper.getPermissionsByEmployeeId(id);
    }

    @Override
    public HSSFWorkbook downloadExcel() {
        // 从数据库中取出列表数据
        List<Employee> allEmployees = employeeMapper.getAllEmployees();

        // 创建Excel并将数据写入
        return createExcel(allEmployees);
    }

    private HSSFWorkbook createExcel(List<Employee> allEmployees) {
        // 创建工作簿(Excel文件)
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 创建sheet(Excel分页)
        HSSFSheet sheet = workbook.createSheet("员工数据");

        // 创建表头(第0行, 及每一列的名称, 直接写死)
        HSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("编号");
        row0.createCell(1).setCellValue("姓名");
        row0.createCell(2).setCellValue("入职时间");
        row0.createCell(3).setCellValue("电话");
        row0.createCell(4).setCellValue("邮箱");
        row0.createCell(5).setCellValue("部门");
        row0.createCell(6).setCellValue("是否是管理员");
        row0.createCell(7).setCellValue("角色");
        row0.createCell(8).setCellValue("状态");

        // 取出每一个员工, 设置每一行数据
        for (int i = 0; i < allEmployees.size(); i++) {
            Employee employee = allEmployees.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(setRowIfNotNull(employee.getUsername()));
            row.createCell(2).setCellValue(setRowInputtime(employee.getInputtime()));
            row.createCell(3).setCellValue(setRowIfNotNull(employee.getTel()));
            row.createCell(4).setCellValue(setRowIfNotNull(employee.getEmail()));
            row.createCell(5).setCellValue(setRowDepartment(employee.getDepartment()));
            row.createCell(6).setCellValue(setRowBoolean(employee.getAdmin(), "是", "否"));
            row.createCell(7).setCellValue(setRowRoles(employee.getRoles()));
            row.createCell(8).setCellValue(setRowBoolean(employee.getState(), "在职", "离职"));
        }

        return workbook;
    }

    private String setRowIfNotNull(String target) {
        return target == null ? "" : target;
    }

    private String setRowBoolean(Boolean admin, String succStr, String failStr) {
        return admin ? succStr : failStr;
    }

    private String setRowInputtime(Date inputtime) {
        if (inputtime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(inputtime);
    }

    private String setRowDepartment(Department department) {
        if (department == null) {
            return "";
        }
        return department.getName();
    }

    private String setRowRoles(List<Role> roles) {
        if (roles == null || roles.size() == 0) {
            return "";
        }
        StringBuilder rolesRes = new StringBuilder();
        for (int i = 0; i < roles.size(); i++) {
            rolesRes.append(roles.get(i).getRname());
            if (i != roles.size() - 1) {
                rolesRes.append(",");
            }
        }
        return rolesRes.toString();
    }

}
