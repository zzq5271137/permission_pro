package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.PageListRes;
import com.mycomp.domain.Permission;
import com.mycomp.domain.Role;
import com.mycomp.domain.RoleQueryVo;
import com.mycomp.mapper.RoleMapper;
import com.mycomp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageListRes getAllRoles(RoleQueryVo queryVo) {
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<Role> roles = roleMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public void saveRole(Role role) {
        // 1. 保存角色
        roleMapper.insert(role);

        // 2. 保存这个角色与权限之间的关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRolePermissionRel(role.getRid(), permission.getPid());
        }
    }

    @Override
    public void updateRole(Role role) {
        // 1. 先打破角色与权限原来的关系
        roleMapper.deleteRolePermissionRel(role.getRid());

        // 2. 更新角色信息
        roleMapper.updateByPrimaryKey(role);

        // 3. 再重新建立关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRolePermissionRel(role.getRid(), permission.getPid());
        }
    }

}
