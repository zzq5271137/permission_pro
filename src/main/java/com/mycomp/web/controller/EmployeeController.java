package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Employee;
import com.mycomp.domain.EmployeelistQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.service.IEmployeeService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class EmployeeController {

    // 注入service层对象
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions("employee:index")
    @RequestMapping("/employee")
    public String employee() {
        return "employee";
    }

    @RequestMapping("/employeelist")
    @ResponseBody  // 返回json数据
    public PageListRes employeelist(EmployeelistQueryVo queryVo) {
        return employeeService.getAllEmployees(queryVo);
    }

    @RequiresPermissions("employee:add")
    @RequestMapping("/saveEmployee")
    @ResponseBody
    public AjaxRes saveEmployee(Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequiresPermissions("employee:edit")
    @RequestMapping("/updateEmployee")
    @ResponseBody
    public AjaxRes updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @RequiresPermissions("employee:delete")
    @RequestMapping("/softDeleteEmployee")
    @ResponseBody
    public AjaxRes softDeleteEmployee(Long id) {
        return employeeService.softDeleteEmployee(id);
    }

    @RequestMapping("/downloadEmpExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response) {
        // 创建工作簿
        HSSFWorkbook workbook = employeeService.downloadExcel();

        // 设置响应
        String fileName = new String("员工数据表.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            // 将工作簿写给response
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/downloadEmpExcelTemplate")
    @ResponseBody
    public void downloadExcelTemplate(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应
        String fileName = new String("EmployeeTpl.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 获取Excel模板文件全路径
        String fileRealPath = request.getSession().getServletContext().getRealPath("static/EmployeeTpl.xls");

        FileInputStream fis = null;
        try {
            // 根据路径读取文件
            fis = new FileInputStream(fileRealPath);

            // 将文件流写给浏览器
            IOUtils.copy(fis, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件上传;
     * 记得一定要配置一个文件上传解析器(配置了才能使用MultipartFile), 详见application-mvc.xml
     */
    @RequestMapping("/uploadEmpExcel")
    @ResponseBody
    public AjaxRes uploadEmpExcel(@RequestParam("excel") MultipartFile excel) {
        return employeeService.uploadEmpExcel(excel);
    }
}
