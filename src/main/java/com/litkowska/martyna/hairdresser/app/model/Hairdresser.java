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
    private long hairdresserId;
    @OneToOne
    @NotNull
    private User user;
    @ManyToOne
    @NotNull
    private Shift shift;

    public long getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(final long hairdresserId) {
        this.hairdresserId = hairdresserId;
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
                "hairdresserId=" + hairdresserId +
                ", shift=" + shift +
//                ", user=" + user.getEmail() +
                '}';
    }
}
