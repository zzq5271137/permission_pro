package com.mycomp.service.impl;

import com.mycomp.domain.Permission;
import com.mycomp.mapper.PermissionMapper;
import com.mycomp.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long rid) {
        return permissionMapper.selectPermissionsByRoleId(rid);
    }

}
