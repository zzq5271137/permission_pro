package com.mycomp.mapper;

import com.mycomp.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    List<Menu> selectAll();

    void insert(Menu record);

    Long getParentIdByPrimaryKey(@Param("id") Long parentId);

    void updateByPrimaryKey(Menu record);

    void deleteParentMenuRel(Long id);

    void deleteByPrimaryKey(Long id);

    List<Menu> getTreeData();

    List<Menu> getChildMenuList();

}