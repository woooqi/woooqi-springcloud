package com.gov.purchase.controller;

import com.gov.purchase.entity.Menu;
import com.gov.purchase.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("feign")
public class FeignController {

    private static String  url = "http://localhost:8900/feign";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/menus/{id}")
    public Menu getMenuById(@PathVariable String id){
        Menu menu = restTemplate.getForObject(url+"/menus/" + id,Menu.class);
        return menu;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id){
        User user = restTemplate.getForObject(url+"/users/" + id,User.class);
        return user;
    }
}
