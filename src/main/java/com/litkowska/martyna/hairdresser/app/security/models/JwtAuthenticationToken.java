package com.litkowska.martyna.hairdresser.app.security.models;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public JwtAuthenticationToken(String token) {
        super(null,null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
