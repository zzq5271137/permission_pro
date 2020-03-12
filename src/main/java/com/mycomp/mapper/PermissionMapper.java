package com.mycomp.mapper;

import com.mycomp.domain.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> selectAll();

    List<Permission> selectPermissionsByRoleId(Long rid);

}