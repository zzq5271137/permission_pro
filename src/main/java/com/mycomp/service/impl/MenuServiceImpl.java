package com.mycomp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Menu;
import com.mycomp.domain.MenuQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.mapper.MenuMapper;
import com.mycomp.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return menuMapper.getTreeData();
    }

}
