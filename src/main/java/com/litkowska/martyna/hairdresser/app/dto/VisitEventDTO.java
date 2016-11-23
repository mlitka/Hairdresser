package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.Visit;

import java.time.LocalDateTime;

/**
 * Created by Martyna on 21.11.2016.
 */
public class VisitEventDTO {
    private long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String info;
    private String hairdresser;

    public VisitEventDTO() {
    }

    public VisitEventDTO(final Visit visit) {
        this.id = visit.getId();
        this.start = LocalDateTime.of(visit.getDate(), visit.getTime());
        this.end = LocalDateTime.of(visit.getDate(), visit.getTime().plusMinutes(visit.getHairService().getDuration()));
        this.title = visit.getHairService().getName();
        this.hairdresser = visit.getHairdresser().getUser().getFirstAndLastName();
        this.info = visit.getHairService().getName() + "\n" + visit.getHairdresser().getUser().getFirstAndLastName() + "\n\n" + visit.getClient().getUser().getInfo();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(String hairdresser) {
        this.hairdresser = hairdresser;
    }
}

