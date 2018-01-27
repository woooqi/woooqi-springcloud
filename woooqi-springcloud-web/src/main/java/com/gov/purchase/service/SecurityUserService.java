package com.gov.purchase.service;

import com.gov.purchase.utils.Md5Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SecurityUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.gov.purchase.entity.User entity = new com.gov.purchase.entity.User();
        entity.setUsername("admin");
        entity.setPassword(Md5Utils.getMD5("123"));
        if(entity==null){
            return null;
        }
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        User user = new User(username, entity.getPassword(), authorities);
        return user;
    }

}
