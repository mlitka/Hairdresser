package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Visit implements Comparable<Visit>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    @Column
//    private Hairdresser hairdresser;
//    @Column
//    private Client client;
    @Column
    private Timestamp timestamp;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

//    public Hairdresser getHairdresser() {
//        return hairdresser;
//    }

//    public void setHairdresser(final Hairdresser hairdresser) {
//        this.hairdresser = hairdresser;
//    }

//    public Client getClient() {
//        return client;
//    }

//    public void setClient(final Client client) {
//        this.client = client;
//    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public int compareTo(final Visit otherVisit) {
        return this.timestamp.compareTo(otherVisit.getTimestamp());
    }
}
