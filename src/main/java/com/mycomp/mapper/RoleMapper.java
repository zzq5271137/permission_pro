package com.mycomp.mapper;

import com.mycomp.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    List<Role> selectAll();

    void insert(Role record);

    void insertRolePermissionRel(@Param("rid") Long rid, @Param("pid") Long pid);

    void deleteRolePermissionRel(Long rid);

    int updateByPrimaryKey(Role record);

    void deleteEmployeeRoleRel(Long rid);

    void deleteByPrimaryKey(Long rid);

    List<Long> getRolesByEmployeeId(Long id);

    Role selectByPrimaryKey(Long rid);
}