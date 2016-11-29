package com.litkowska.martyna.hairdresser.app.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Martyna on 23.11.2016.
 */
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key +" : "+value);
//        }
//        String header = request.getHeader("Authorization");
//        System.out.println("\n\ncors!\n"+header);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null
                && "OPTIONS".equals(request.getMethod())) {
// CORS "pre-flight" request
            response.setHeader("Access-Control-Allow-Credentials",
                    "true");
            response.setHeader("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE");
            response.setHeader("Access-Control-Allow-Headers",
                    "X-Requested-With,Origin,Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Max-Age",
                    "100");
        }

        filterChain.doFilter(request, response);
    }

}