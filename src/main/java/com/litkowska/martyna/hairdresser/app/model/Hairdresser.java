package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Hairdresser{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    @NotNull
    private Shift shift;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Hairdresser{" +
                "id=" + id +
                ", shift=" + shift +
                ", user=" + user.getEmail() +
                '}';
    }
}
