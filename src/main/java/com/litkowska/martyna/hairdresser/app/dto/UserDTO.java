package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;

/**
 * Created by Martyna on 21.11.2016.
 *
 * Data transfer object to return logged user data
 */
public class UserDTO {
    private long id;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNo;
    private AuthRole role;

    public UserDTO() {
    }

    public UserDTO(final User user) {
        this.id = user.getId();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.phoneNo = user.getPhoneNo();
        this.role = user.getRole();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }
}
