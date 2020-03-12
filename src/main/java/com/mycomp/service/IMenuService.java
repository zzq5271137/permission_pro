package com.mycomp.service;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Menu;
import com.mycomp.domain.MenuQueryVo;
import com.mycomp.domain.PageListRes;

import java.util.List;

public interface IMenuService {

    PageListRes getMenuList(MenuQueryVo queryVo);

    List<Menu> getAllMenus();

    AjaxRes saveMenu(Menu menu);

    AjaxRes updateMenu(Menu menu);

    AjaxRes deleteMenu(Long id);

    List<Menu> getTreeData();
}
