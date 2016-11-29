package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.Opinion;

import java.time.LocalDateTime;

/**
 * Created by Martyna on 19.11.2016.
 */
public class OpinionDTO {
    private long opinionId;
    private long userId = -1;
    private String text;
    private int rate = -1;
    private LocalDateTime dateTime;
    private String author;

    public OpinionDTO(){

    }

    public OpinionDTO(final Opinion opinion){
        this.opinionId = opinion.getId();
        this.userId = opinion.getClient().getUser().getId();
        this.text = opinion.getOpinionText();
        this.rate = opinion.getRate();
        this.dateTime = opinion.getDateTime();
        this.author = opinion.getClient().getUser().getFirstAndLastName();
    }

    public long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(long opinionId) {
        this.opinionId = opinionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
