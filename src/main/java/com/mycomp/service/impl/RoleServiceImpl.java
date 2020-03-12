package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.*;
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
    public PageListRes getRolelist(RoleQueryVo queryVo) {
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<Role> roles = roleMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public AjaxRes saveRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            // 1. 保存角色
            roleMapper.insert(role);

            // 2. 保存这个角色与权限之间的关系
            for (Permission permission : role.getPermissions()) {
                roleMapper.insertRolePermissionRel(role.getRid(), permission.getPid());
            }

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("角色保存成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("角色保存失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes updateRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            // 1. 先打破角色与权限原来的关系
            roleMapper.deleteRolePermissionRel(role.getRid());

            // 2. 更新角色信息
            roleMapper.updateByPrimaryKey(role);

            // 3. 再重新建立关系
            for (Permission permission : role.getPermissions()) {
                roleMapper.insertRolePermissionRel(role.getRid(), permission.getPid());
            }

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("角色更新成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("角色更新失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes deleteRole(Long rid) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            // 1. 先打破角色与权限原来的关系
            roleMapper.deleteRolePermissionRel(rid);

            // 2. 还要打破角色与员工原来的关系
            roleMapper.deleteEmployeeRoleRel(rid);

            // 3. 再删除角色
            roleMapper.deleteByPrimaryKey(rid);

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("角色删除成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("角色删除失败...");
        }
        return ajaxRes;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Long> getRolesByEmployeeId(Long id) {
        return roleMapper.getRolesByEmployeeId(id);
    }

}
