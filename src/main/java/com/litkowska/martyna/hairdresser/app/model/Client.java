package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Client extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany
    private List<Visit> visits;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(final long id) {
        this.id = id;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(final List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", visits=" + visits +
                '}';
    }
}
