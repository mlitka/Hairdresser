package com.litkowska.martyna.hairdresser.app.security.models;


public class JwtUser {

    private String userId;
    private AuthRole role;

    public JwtUser (){

    }
    public JwtUser(final String userId, final AuthRole role) {
        this.userId = userId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }
}
