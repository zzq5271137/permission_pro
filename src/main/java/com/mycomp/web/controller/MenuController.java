package com.mycomp.web.controller;

import com.mycomp.domain.AjaxRes;
import com.mycomp.domain.Menu;
import com.mycomp.domain.MenuQueryVo;
import com.mycomp.domain.PageListRes;
import com.mycomp.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }

    @RequestMapping("/menulist")
    @ResponseBody
    public PageListRes menulist(MenuQueryVo queryVo) {
        return menuService.getMenuList(queryVo);
    }

    @RequestMapping("/getAllMenus")
    @ResponseBody
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @RequestMapping("/saveMenu")
    @ResponseBody
    public AjaxRes saveMenu(Menu menu) {
        return menuService.saveMenu(menu);
    }

    @RequestMapping("/updateMenu")
    @ResponseBody
    public AjaxRes updateMenu(Menu menu) {
        return menuService.updateMenu(menu);
    }

    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxRes deleteMenu(Long id) {
        return menuService.deleteMenu(id);
    }

    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData() {
        return menuService.getTreeData();
    }

}
