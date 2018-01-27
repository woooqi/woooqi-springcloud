package com.woooqi.springcloud.service;


import com.gov.purchase.entity.Menu;

import java.util.List;


public interface MenuService {

    public List<Menu> getMenus();

    public Menu getMenuById(String id);

    Integer insertMenu(Menu menu);
}
