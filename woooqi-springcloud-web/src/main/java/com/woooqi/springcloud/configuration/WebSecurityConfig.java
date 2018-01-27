package com.woooqi.springcloud.configuration;

import com.gov.purchase.configuration.security.AjaxFormAuthenticationFailureHandler;
import com.gov.purchase.configuration.security.AjaxFormAuthenticationSuccessHandler;
import com.gov.purchase.configuration.security.KaptchaAuthenticationFilter;
import com.gov.purchase.configuration.security.KaptchaAuthenticationProvider;
import com.gov.purchase.service.SecurityUserService;
import com.woooqi.springcloud.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Autowired
    private KaptchaAuthenticationProvider kaptchaAuthenticationProvider;

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    @Autowired
    private SecurityUserService securityUserService;

    @Bean
    public AjaxFormAuthenticationSuccessHandler getSuccessHandler(String defaultSuccessUrl){
        return new AjaxFormAuthenticationSuccessHandler(defaultSuccessUrl);
    };

    @Bean
    public AjaxFormAuthenticationFailureHandler getFailureHandler(String defaultFailureUrl){
        return new AjaxFormAuthenticationFailureHandler(defaultFailureUrl);
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new KaptchaAuthenticationFilter(ehCacheCacheManager,"/login?error"),UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login")
            .successHandler(getSuccessHandler("/index")).failureHandler(getFailureHandler("/login?error"))
                .authenticationDetailsSource(authenticationDetailsSource).permitAll()
            .and()
            .logout().logoutSuccessUrl("/login").permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().maximumSessions(1);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/**/favicon.ico");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(kaptchaAuthenticationProvider);
    }

}