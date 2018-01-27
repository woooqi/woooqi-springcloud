package com.gov.purchase.configuration.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class AppWebAuthenticationDetails extends WebAuthenticationDetails {

    private String type;

    public AppWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.type = request.getParameter("type");
    }

    public String getType() {
        return type;
    }

    public void setType(String machine) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; type: ").append(this.getType());
        return sb.toString();
    }
}
