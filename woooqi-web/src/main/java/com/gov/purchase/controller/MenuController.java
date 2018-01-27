package com.gov.purchase.controller;

import com.gov.purchase.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("menus")
public class MenuController {

    private static String  url = "http://localhost:8900/menu/menus";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public List<Menu> getMenus(){
        ParameterizedTypeReference<List<Menu>> clazz = new ParameterizedTypeReference<List<Menu>>() {};
        ResponseEntity<List<Menu>> responseEntity = restTemplate.exchange(url+ "/all", HttpMethod.GET, new HttpEntity(null), clazz);
        List<Menu> menus = responseEntity.getBody();
        return menus;
    }
    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable String id){
        Menu menu = restTemplate.getForObject(url+"/" + id,Menu.class);
        return menu;
    }

}
