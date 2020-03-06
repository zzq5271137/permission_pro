package com.mycomp.web.controller;

import com.mycomp.domain.Permission;
import com.mycomp.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList() {
        return permissionService.getAllPermissions();
    }

    @RequestMapping("/getPermissionsByRoleId")
    @ResponseBody
    public List<Permission> getPermissionsByRoleId(Long rid) {
        return permissionService.getPermissionsByRoleId(rid);
    }

}
