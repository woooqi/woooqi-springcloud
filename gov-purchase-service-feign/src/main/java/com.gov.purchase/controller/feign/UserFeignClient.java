package com.gov.purchase.controller.feign;

import com.gov.purchase.controller.hystric.UserHystric;
import com.gov.purchase.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "SERVICE-USER",fallback = UserHystric.class)
public interface UserFeignClient {

    @RequestMapping("/users/{id}")
    public User getUserById(@PathVariable("id") String id);
}
