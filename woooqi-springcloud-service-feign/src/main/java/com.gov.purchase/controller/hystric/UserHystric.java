package com.gov.purchase.controller.hystric;

import com.gov.purchase.controller.feign.UserFeignClient;
import com.gov.purchase.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserHystric implements UserFeignClient {
    @Override
    public User getUserById(String id) {
        User user = new User();
        user.setUsername("ErrorUser");
        return user;
    }
}
