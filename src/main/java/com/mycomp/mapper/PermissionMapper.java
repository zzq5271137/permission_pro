package com.mycomp.mapper;

import com.mycomp.domain.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> selectAll();

    List<Permission> selectPermissionsByRoleId(Long rid);

    int deleteByPrimaryKey(Long pid);

    int insert(Permission record);

    int updateByPrimaryKey(Permission record);

    Permission selectByPrimaryKey(Long pid);
}