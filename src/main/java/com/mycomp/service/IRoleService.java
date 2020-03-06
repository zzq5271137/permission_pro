package com.mycomp.service;

import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;

public interface IRoleService {

    PageListRes getAllRoles(RoleQueryVo queryVo);

    void saveRole(Role role);

    void updateRole(Role role);
}
