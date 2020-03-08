package com.mycomp.service;

import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;

import java.util.List;

public interface IRoleService {

    PageListRes getRolelist(RoleQueryVo queryVo);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long rid);

    List<Role> getAllRoles();

    List<Long> getRolesByEmployeeId(Long id);
}
