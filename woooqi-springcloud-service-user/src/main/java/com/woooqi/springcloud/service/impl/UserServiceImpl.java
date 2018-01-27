package com.woooqi.springcloud.service.impl;

import com.github.pagehelper.PageHelper;
import com.gov.purchase.dao.UserMapper;
import com.gov.purchase.entity.User;
import com.gov.purchase.service.UserService;
import com.woooqi.springcloud.dao.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        PageHelper.startPage(1,10);
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public User getUserById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Transactional
    @Override
    public Integer insertUser(User user) {

        return userMapper.insert(user);
    }
}
