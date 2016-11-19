package com.litkowska.martyna.hairdresser.app.dto;

import javax.persistence.Lob;
import java.time.LocalDateTime;

/**
 * Created by Martyna on 19.11.2016.
 */
public class OpinionDTO {
    private long opinionId;
    private long clientID = -1;
    private Lob text;
    private int rate = -1;
    private LocalDateTime dateTime;

    public long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(long opinionId) {
        this.opinionId = opinionId;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public Lob getText() {
        return text;
    }

    public void setText(Lob text) {
        this.text = text;
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
}
