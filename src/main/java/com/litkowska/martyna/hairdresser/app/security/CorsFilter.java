package com.litkowska.martyna.hairdresser.app.security;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Martyna on 23.11.2016.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key +" : "+value);
        }
        String header = request.getHeader("Authorization");
        System.out.println("\n\ncors!\n"+header);
        String header1 = request.getHeader("authorization");
        System.out.println("\n\ncors!\n"+header1);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
        if (request.getMethod()!="OPTIONS") {
            chain.doFilter(req, res);
        } else {
        }
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}