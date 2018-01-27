package com.woooqi.springcloud.controller;

import com.gov.purchase.entity.User;
import com.gov.purchase.service.UserService;
import com.woooqi.springcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("users")
public class UserController {

    @Value("${profile}")
    private String profile;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getUsers(){
        List<User> list = userService.getUsers();
        return list;
    }
    @GetMapping("/config")
    public String getConfig(){
        return profile;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id){
        User user = userService.getUserById(id);
        return user;
    }

    @PostMapping("/add")
    public void addUser(){
        User user = new User();
        user.setUsername("用户");
        Integer count = userService.insertUser(user);
        System.out.println(user.getId());
    }

}
