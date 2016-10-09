package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

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
    private Time time;
    @Column
    private Date date;
    @OneToOne
    private HairService hairService;

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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HairService getHairService() {
        return hairService;
    }

    public void setHairService(HairService hairService) {
        this.hairService = hairService;
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
                '}';
    }
}
