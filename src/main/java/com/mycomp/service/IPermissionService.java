package com.mycomp.service;

import com.mycomp.domain.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> getAllPermissions();

    List<Permission> getPermissionsByRoleId(Long rid);
}
