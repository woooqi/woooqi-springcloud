package com.woooqi.springcloud.controller;

import com.gov.purchase.configuration.security.KaptchaBadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView getLogin(HttpServletRequest request, ModelAndView model){
        Object spring_security_last_exception = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        Integer loginCount = 0;
        if(spring_security_last_exception!=null) {
            String message = ((KaptchaBadCredentialsException) spring_security_last_exception).getMessage();
            loginCount = ((KaptchaBadCredentialsException) spring_security_last_exception).getLoginCount();
            if (message != null && !"".equals(message)) {
                if(message.indexOf("EmptyKaptcheException")!=-1) {
                    model.addObject("errormsg", "验证码不能为空");
                } else if(message.indexOf("EmptyAccountException")!=-1) {
                    model.addObject("errormsg", "密码不能为空");
                } else if(message.indexOf("IncorrectKaptcheException")!=-1) {
                    model.addObject("errormsg", "验证码有误");
                } else if (message.indexOf("UnknownAccountException") != -1) {
                    model.addObject("errormsg", "用户不存在");
                } else if (message.indexOf("ExcessiveAttemptsException") != -1) {
                    model.addObject("errormsg", "密码错误次数过多，账户锁定10分钟");
                } else if (message.indexOf("IncorrectCredentialsException")!=-1) {
                    model.addObject("errormsg", "密码有误");
                }else{
                    model.addObject("errormsg", "系统错误,请联系管理员");
                }
            }
        }

        model.addObject("loginCount", loginCount);
        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",null);
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/index")
    public ModelAndView getIndex(ModelAndView model){
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/table")
    public ModelAndView getHome(ModelAndView model){
        model.setViewName("view/base/table");
        return model;
    }

}
