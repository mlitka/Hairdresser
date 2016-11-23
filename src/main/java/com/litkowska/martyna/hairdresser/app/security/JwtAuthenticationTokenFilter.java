package com.litkowska.martyna.hairdresser.app.security;

import com.litkowska.martyna.hairdresser.app.security.exceptions.JwtMissingTokenException;
import com.litkowska.martyna.hairdresser.app.security.models.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {


    public JwtAuthenticationTokenFilter(){
        super("/auth/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

//
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            System.out.println(key +" : "+value);
        }
        String header = httpServletRequest.getHeader("authorization");
        System.out.println("\n\nheader!\n"+header);

        if (header == null || !header.startsWith("Bearer ")){
            throw new JwtMissingTokenException("No JWT token found in request header");
        }

        String token = header.substring(7);

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);

        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException{

        super.successfulAuthentication(request,response,chain,authResult);

        chain.doFilter(request, response);


    }
}
