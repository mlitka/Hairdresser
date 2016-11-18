package com.litkowska.martyna.hairdresser.app.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtMissingTokenException extends AuthenticationException {

    public JwtMissingTokenException(String msg){
        super(msg);
    }
}
