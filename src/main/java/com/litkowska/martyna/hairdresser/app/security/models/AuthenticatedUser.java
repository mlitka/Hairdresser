package com.litkowska.martyna.hairdresser.app.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class AuthenticatedUser implements UserDetails {

    private String userId;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUser(String userId, String token, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.token = token;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getToken(){
        return token;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
