package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;
import com.mycomp.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("role:index")
    @RequestMapping("/role")
    public String role() {
        return "role";
    }

    @RequestMapping("/rolelist")
    @ResponseBody
    public PageListRes rolelist(RoleQueryVo queryVo) {
        return roleService.getRolelist(queryVo);
    }

    @RequiresPermissions("role:add")
    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role) {
        return roleService.saveRole(role);
    }

    @RequiresPermissions("role:edit")
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role) {
        return roleService.updateRole(role);
    }

    @RequiresPermissions("role:delete")
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid) {
        return roleService.deleteRole(rid);
    }

    @RequestMapping("/getAllRoles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @RequestMapping("/getRolesByEmployeeId")
    @ResponseBody
    public List<Long> getRolesByEmployeeId(Long id) {
        return roleService.getRolesByEmployeeId(id);
    }
}
