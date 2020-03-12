package com.mycomp.service;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;

import java.util.List;

public interface IRoleService {

    PageListRes getRolelist(RoleQueryVo queryVo);

    AjaxRes saveRole(Role role);

    AjaxRes updateRole(Role role);

    AjaxRes deleteRole(Long rid);

    List<Role> getAllRoles();

    List<Long> getRolesByEmployeeId(Long id);
}
