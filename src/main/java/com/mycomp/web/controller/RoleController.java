package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;
import com.mycomp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/role")
    public String role() {
        return "role";
    }

    @RequestMapping("/rolelist")
    @ResponseBody
    public PageListRes rolelist(RoleQueryVo queryVo) {
        return roleService.getRolelist(queryVo);
    }

    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.saveRole(role);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("保存成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败...");
        }
        return ajaxRes;
    }

    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.updateRole(role);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("更新成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("更新失败...");
        }
        return ajaxRes;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.deleteRole(rid);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("删除成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败...");
        }
        return ajaxRes;
    }

    @RequestMapping("/getAllRoles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
