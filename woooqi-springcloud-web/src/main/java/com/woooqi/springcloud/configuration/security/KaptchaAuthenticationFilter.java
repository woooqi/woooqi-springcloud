package com.woooqi.springcloud.configuration.security;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private EhCacheCacheManager ehCacheCacheManager;

    public final static String SESSION_KAPTCHA_KEY = "__kaptche_key";

    public KaptchaAuthenticationFilter(EhCacheCacheManager ehCacheCacheManager,String defaultFailureUrl) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.ehCacheCacheManager = ehCacheCacheManager;
        setAuthenticationFailureHandler(new AjaxFormAuthenticationFailureHandler(defaultFailureUrl));
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse)response;
        Integer loginCount = 0;

        if("POST".equalsIgnoreCase(req.getMethod()) && req.getParameter("token") == null && req.getParameter("machine") == null ){
            Cache cache = ehCacheCacheManager.getCacheManager().getCache("passwordRetryCache");
            Element element = cache.get(req.getParameter("username"));
            loginCount = (element!=null && element.getObjectValue()!=null)?(Integer)element.getObjectValue():0;
            if(loginCount != null && loginCount != 0) {
                if(loginCount>=3){
                    unsuccessfulAuthentication(req, res, new KaptchaBadCredentialsException("ExcessiveAttemptsException",loginCount));
                    return;
                }
                String kaptcha = this.obtainKaptcha(req);
                String kaptcha_code = this.obtainSessionKaptcha(req);
                if (StringUtils.isEmpty(kaptcha_code) || StringUtils.isEmpty(kaptcha)) {
                    unsuccessfulAuthentication(req, res, new KaptchaBadCredentialsException("EmptyKaptcheException",loginCount));
                    return;
                } else if (!kaptcha.equalsIgnoreCase(kaptcha_code)) {
                    unsuccessfulAuthentication(req, res, new KaptchaBadCredentialsException("IncorrectKaptcheException",loginCount));
                    return;
                }
            }
        }
        chain.doFilter(request,response);
    }

    protected String obtainKaptcha(HttpServletRequest request){
        return request.getParameter("kaptcha");
    }

    protected String obtainSessionKaptcha (HttpServletRequest request){
        String kaptcha_code = (String)request.getSession().getAttribute(SESSION_KAPTCHA_KEY);
        request.getSession().setAttribute(SESSION_KAPTCHA_KEY,"");
        return kaptcha_code;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
