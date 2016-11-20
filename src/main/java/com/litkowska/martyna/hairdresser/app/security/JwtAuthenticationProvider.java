package com.litkowska.martyna.hairdresser.app.security;

import com.litkowska.martyna.hairdresser.app.security.exceptions.JwtTokenInvalidException;
import com.litkowska.martyna.hairdresser.app.security.models.AuthenticatedUser;
import com.litkowska.martyna.hairdresser.app.security.models.JwtAuthenticationToken;
import com.litkowska.martyna.hairdresser.app.security.models.JwtUser;
import com.litkowska.martyna.hairdresser.app.security.utilities.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    JwtToken jwtToken;

    @Override
    public boolean supports(Class<?> authentication){
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }



    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
            throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken auth)
            throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) auth;
        String token = jwtAuthenticationToken.getToken();

        JwtUser user = jwtToken.decode(token);

        if (user == null){
            throw new JwtTokenInvalidException("Token is invalid");
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().toString());

        return new AuthenticatedUser(user.getUserId(), token, authorityList);

    }
}
