package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.*;
import com.mycomp.mapper.MenuMapper;
import com.mycomp.service.IMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageListRes getMenuList(MenuQueryVo queryVo) {
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<Menu> menus = menuMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(menus);
        return pageListRes;
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.selectAll();
    }

    @Override
    public AjaxRes saveMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            menuMapper.insert(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单保存成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单保存失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes updateMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();

        // 判断父菜单是不是自己的子菜单
        if (menu.getParent().getId() != null) {
            Long parentId = menu.getParent().getId();
            Long parentParentId = menuMapper.getParentIdByPrimaryKey(parentId);
            if (parentParentId != null && parentParentId.equals(menu.getId())) {
                ajaxRes.setSuccess(false);
                ajaxRes.setMsg("父菜单不能设置为自己的子菜单...");
                return ajaxRes;
            }
        }

        try {
            menuMapper.updateByPrimaryKey(menu);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单更新成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单更新失败...");
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes deleteMenu(Long id) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            // 1. 先打破父子菜单关系
            menuMapper.deleteParentMenuRel(id);

            // 2. 删除菜单
            menuMapper.deleteByPrimaryKey(id);

            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("菜单删除成功！");
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("菜单删除失败...");
        }
        return ajaxRes;
    }

    @Override
    public List<Menu> getTreeData() {
        List<Menu> treeData = menuMapper.getTreeData();

        /*
         * 权限控制, 根据当前用户的角色和权限, 返回不同的菜单目录树;
         * 通过Shiro中的SecurityUtils去获得当前用户, 从而进一步获取相关信息(该用户的角色和权限等);
         */
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee) subject.getPrincipal();
        // 判断其是否为管理员
        if (employee.getAdmin() == null || !employee.getAdmin()) {
            // 如果不是管理员, 需要做权限检查(根据当前用户的权限, 对treeData进行修改)
            checkPermissions(treeData);
        }

        return treeData;
    }

    private void checkPermissions(List<Menu> treeData) {
        // 获取主体(当前用户)
        Subject subject = SecurityUtils.getSubject();

        // 遍历所有菜单及子菜单(必须使用迭代器, 因为要一边校验一边删除)
        Iterator<Menu> iterator = treeData.iterator();
        while (iterator.hasNext()) {
            Menu menu = iterator.next();

            // 先校验自己
            if (menu.getPermission() != null) {
                String presource = menu.getPermission().getPresource();
                if (presource != null && !subject.isPermitted(presource)) {
                    iterator.remove();
                }
            }

            // 判断是否有子菜单, 如果有, 继续校验(递归调用)
            if (menu.getChildren().size() > 0) {
                checkPermissions(menu.getChildren());
            }
        }
    }

}
