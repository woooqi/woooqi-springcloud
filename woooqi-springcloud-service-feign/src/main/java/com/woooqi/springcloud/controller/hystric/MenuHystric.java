package com.woooqi.springcloud.controller.hystric;

import com.gov.purchase.controller.feign.MenuFeignClient;
import com.gov.purchase.entity.Menu;
import com.woooqi.springcloud.controller.feign.MenuFeignClient;
import org.springframework.stereotype.Component;

@Component
public class MenuHystric implements MenuFeignClient {
    @Override
    public Menu getMenuById(String id) {
        Menu menu = new Menu();
        menu.setName("ErrorMenu");
        return menu;
    }
}
