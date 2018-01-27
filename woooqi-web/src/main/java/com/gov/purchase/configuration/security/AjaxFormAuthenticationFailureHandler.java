package com.gov.purchase.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gov.purchase.entity.Page;
import com.gov.purchase.utils.Md5Utils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjaxFormAuthenticationFailureHandler implements AuthenticationFailureHandler{

    private String defaultFailureUrl;

    public AjaxFormAuthenticationFailureHandler() {
    }

    public AjaxFormAuthenticationFailureHandler(String defaultFailureUrl) {
        this.defaultFailureUrl = (defaultFailureUrl);
    }

    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
        HttpSession session = request.getSession(false);
        if (session != null ) {
            request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        }
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        String machine = httpServletRequest.getParameter("machine");
        String username = httpServletRequest.getParameter("username");
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) && (machine==null || "".equals(machine))) {
            if (this.defaultFailureUrl == null) {
                httpServletResponse.sendError(401, "Authentication Failed: " + exception.getMessage());
            } else {
                saveException(httpServletRequest, exception);
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, this.defaultFailureUrl);
            }
        }else{
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/html; charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            String message = exception.getMessage();
            String token = Md5Utils.getMD5(username+machine);
            List<Map<String,Object>> list = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("username",username);
            map.put("token",token);
            list.add(map);
            Page<Map<String,Object>> page = new Page<Map<String,Object>>();
            page.setDatas(list);
            page.setTotal(list.size());

            if (message != null && !"".equals(message)) {
                if (message.indexOf("EmptyKaptcheException")!=-1) {
                    page.setCode(-89);
                    page.setMessage("验证码不能为空");
                } else if (message.indexOf("EmptyAccountException")!=-1) {
                    page.setCode(-90);
                    page.setMessage("密码不能为空");
                } else if (message.indexOf("IncorrectKaptcheException")!=-1) {
                    page.setCode(-93);
                    page.setMessage("验证码错误");
                } else if (message.indexOf("UnknownAccountException") != -1) {
                    page.setCode(-91);
                    page.setMessage("账号不存在");
                } else if (message.indexOf("ExcessiveAttemptsException") != -1) {
                    page.setCode(-92);
                    page.setMessage("密码错误次数过多，账户锁定10分钟");
                } else if (message.indexOf("IncorrectCredentialsException")!=-1) {
                    page.setCode(-94);
                    page.setMessage("密码有误");
                }else{
                    page.setCode(-99);
                    page.setMessage(exception.getMessage());
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            out.print(mapper.writeValueAsString(page));
            out.flush();
            out.close();
        }

    }

}
