package com.gov.purchase.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.purchase.entity.Page;
import com.gov.purchase.utils.Md5Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxFormAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String defaultSuccessUrl;

    public AjaxFormAuthenticationSuccessHandler() {
    }

    public AjaxFormAuthenticationSuccessHandler(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String machine = httpServletRequest.getParameter("machine");
        String username = httpServletRequest.getParameter("username");
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) && (machine==null || "".equals(machine))) {
            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
            if(savedRequest != null) {
                String targetUrl = savedRequest.getRedirectUrl();
                if(targetUrl.indexOf("login") != -1){
                    targetUrl =  httpServletRequest.getContextPath() + this.defaultSuccessUrl;
                }
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
            }else{
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, httpServletRequest.getContextPath() + this.defaultSuccessUrl);
            }
            requestCache.removeRequest(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html; charset=utf-8");
            String token = Md5Utils.getMD5(username+machine);
            PrintWriter out = httpServletResponse.getWriter();
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("token",token);
            list.add(map);
            Page<Map<String,Object>> page = new Page<Map<String,Object>>();
            page.setCode(1);
            page.setDatas(list);
            page.setTotal(list.size());
            page.setMessage("success");
            ObjectMapper mapper = new ObjectMapper();
            out.println(mapper.writeValueAsString(page));
            out.flush();
            out.close();
        }

    }



}
