package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Client extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    @OneToMany(mappedBy = "client")
//    private List<Visit> visits;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(final long id) {
        this.id = id;
    }

//    public List<Visit> getVisits() {
//        return visits;
//    }
//
//    public void setVisits(final List<Visit> visits) {
//        this.visits = visits;
//    }


}
