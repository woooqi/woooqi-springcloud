package com.gov.purchase.service;


import com.gov.purchase.entity.User;

import java.util.List;


public interface UserService {

    public List<User> getUsers();

    public User getUserById(String id);

    Integer insertUser(User user);
}
