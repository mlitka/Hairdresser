package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Martyna on 28.10.2016.
 */
@Entity
public class Opinion implements Comparable<Opinion>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @NotNull
    private Client client;
    @Column
    @NotNull
    private String opinionText;
    @Column
    @NotNull
    private int rate;
    @Column
    @NotNull
    private LocalDateTime dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOpinionText() {
        return opinionText;
    }

    public void setOpinionText(String opinionText) {
        this.opinionText = opinionText;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public int compareTo(Opinion o) {
        return Integer.compare(rate, o.getRate());
    }
}
