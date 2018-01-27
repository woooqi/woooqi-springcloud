package com.gov.purchase.controller;

import com.gov.purchase.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("users")

public class UserController {

    private static String  url = "http://localhost:8900/user/users";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public List<User> getUsers(){
        ParameterizedTypeReference<List<User>> clazz = new ParameterizedTypeReference<List<User>>() {};
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url+ "/all", HttpMethod.GET, new HttpEntity(null), clazz);
        List<User> users = responseEntity.getBody();
        return users;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id){
        User user = restTemplate.getForObject(url+"/" + id,User.class);
        return user;
    }
}
