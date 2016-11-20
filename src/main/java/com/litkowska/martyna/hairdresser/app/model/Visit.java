package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Visit implements Comparable<Visit>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Hairdresser hairdresser;
    @Column
    private LocalTime time;
    @Column
    private LocalDate date;
    @OneToOne
    private HairService hairService;
    @ManyToOne
    private Client client;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Hairdresser getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(final Hairdresser hairdresser) {
        this.hairdresser = hairdresser;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HairService getHairService() {
        return hairService;
    }

    public void setHairService(HairService hairService) {
        this.hairService = hairService;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client user) {
        this.client = user;
    }

    @Override
    public int compareTo(final Visit otherVisit) {
        return this.time.compareTo(otherVisit.getTime());
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", hairdresser=" + hairdresser +
                ", time=" + time +
                ", date=" + date +
                ", hairService=" + hairService +
                ", client=" + client.getUser().getEmail() +
                '}';
    }
}
