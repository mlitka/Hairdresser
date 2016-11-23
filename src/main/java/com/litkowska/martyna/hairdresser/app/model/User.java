package com.litkowska.martyna.hairdresser.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.litkowska.martyna.hairdresser.app.security.models.AuthRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Martyna on 26.09.2016.
 */
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String lastName;
    @Column
    private String firstName;
    @Column
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @Column(unique = true)
    private String username;
    @Column
    private String phoneNo;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthRole role;

    public User() {
        role = AuthRole.USER;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
        this.username = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkNotNull() {
        return email != null && !email.isEmpty()
                && lastName != null && !lastName.isEmpty()
                && firstName != null && !firstName.isEmpty();
    }

    public boolean checkPassword() {
        return password != null && !password.isEmpty();
    }

    public String getFirstAndLastName() {
        return this.firstName + " " + this.lastName;
    }

    public String getInfo(){
        return this.getFirstAndLastName() + "\n" + this.email + "\n" + this.phoneNo;
    }
}
