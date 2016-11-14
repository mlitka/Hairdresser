package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Visit> visits = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(final List<Visit> visits) {
        this.visits = visits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", visits=" + visits +
                ", user=" + user.getEmail() +
                '}';
    }
}
