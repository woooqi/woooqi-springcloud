package com.gov.purchase.controller;

import com.gov.purchase.entity.Menu;
import com.gov.purchase.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("menus")
public class MenuController {

    @Value("${profile}")
    private String profile;

    @Autowired
    private MenuService menuService;

    @GetMapping("/all")
    public List<Menu> getUsers(){
        List<Menu> list = menuService.getMenus();
        return list;
    }

    @GetMapping("/config")
    public String getConfig(){
        return profile;
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id){
        Menu menu = menuService.getMenuById(id);
        return menu;
    }

    @GetMapping("/add")
    public void addMenu(){
        Menu menu = new Menu();
        menu.setName("菜单");
        Integer count = menuService.insertMenu(menu);
        System.out.println(menu.getId());
    }

}
