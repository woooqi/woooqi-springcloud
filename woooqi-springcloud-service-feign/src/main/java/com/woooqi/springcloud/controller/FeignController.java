package com.woooqi.springcloud.controller;

import com.gov.purchase.controller.feign.MenuFeignClient;
import com.gov.purchase.controller.feign.UserFeignClient;
import com.gov.purchase.entity.Menu;
import com.gov.purchase.entity.User;
import com.woooqi.springcloud.controller.feign.MenuFeignClient;
import com.woooqi.springcloud.controller.feign.UserFeignClient;
import com.woooqi.springcloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private MenuFeignClient menuFeignClient;

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable String id){
        User user = userFeignClient.getUserById(id);
        return user;
    }
    @RequestMapping("/menus/{id}")
    public Menu getMenu(@PathVariable String id){
        Menu menu = menuFeignClient.getMenuById(id);
        return menu;
    }

}
