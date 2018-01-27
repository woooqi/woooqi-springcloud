package com.woooqi.springcloud.configuration.security;

import com.gov.purchase.service.SecurityUserService;
import com.gov.purchase.utils.Md5Utils;
import com.woooqi.springcloud.service.SecurityUserService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class KaptchaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = (UserDetails)securityUserService.loadUserByUsername(authentication.getName());

        AppWebAuthenticationDetails details = (AppWebAuthenticationDetails) authentication.getDetails();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        String type = details.getType();

        Cache cache = ehCacheCacheManager.getCacheManager().getCache("passwordRetryCache");
        Element element = cache.get(username);
        Integer loginCount = 0;
        if(element == null) {
            element = new Element(username, loginCount);
            cache.put(element);
        }else{
            int maxLoginCount = 3;
            loginCount = (Integer)element.getObjectValue();
            if(loginCount>=maxLoginCount){
                throw new KaptchaBadCredentialsException("ExcessiveAttemptsException",loginCount);
            }
        }

        if(userDetails == null){
            throw new KaptchaBadCredentialsException("UnknownAccountException",loginCount);
        }else if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new KaptchaBadCredentialsException("EmptyAccountException",loginCount);
        }else if(!Md5Utils.getMD5(password).equals(userDetails.getPassword())){
            element = new Element(username, ++loginCount);
            cache.put(element);
            throw new KaptchaBadCredentialsException("IncorrectCredentialsException",loginCount);
        }else {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            cache.remove(username);
            return result;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
