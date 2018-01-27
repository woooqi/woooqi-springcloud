package com.gov.purchase.configuration.security;

import org.springframework.security.authentication.BadCredentialsException;

public class KaptchaBadCredentialsException extends BadCredentialsException {

    private Integer loginCount;

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public KaptchaBadCredentialsException(String msg) {
        super(msg);
    }

    public KaptchaBadCredentialsException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaptchaBadCredentialsException(String msg, Integer loginCount) {
        super(msg);
        this.loginCount = loginCount;
    }

}
