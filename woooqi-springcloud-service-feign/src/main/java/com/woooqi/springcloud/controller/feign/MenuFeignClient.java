package com.woooqi.springcloud.controller.feign;

import com.gov.purchase.controller.hystric.MenuHystric;
import com.gov.purchase.entity.Menu;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "SERVICE-MENU",fallback = MenuHystric.class)
public interface MenuFeignClient {

    @RequestMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable("id") String id);
}
