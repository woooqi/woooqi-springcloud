package com.woooqi.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.gov.purchase.dao.MenuMapper;
import com.gov.purchase.entity.Menu;
import com.gov.purchase.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenus() {
        PageHelper.startPage(1,10);
        List<Menu> menus = menuMapper.selectAll();
        return menus;
    }

    @Override
    public Menu getMenuById(String id) {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        return menu;
    }

    @Transactional
    @Override
    public Integer insertMenu(Menu menu) {

        return menuMapper.insert(menu);
    }
}
