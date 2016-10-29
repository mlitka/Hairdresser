package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.Visit;

/**
 * Created by Martyna on 29.10.2016.
 */
public class VisitProposalDTO {
    private String date;
    private String time;
    private String endTime;

    public VisitProposalDTO() {
    }

    public VisitProposalDTO(final Visit visit) {
        this.date = visit.getDate().toString();
        this.time = visit.getTime().toString();
        this.endTime = visit.getTime().plusMinutes(visit.getHairService().getDuration()).toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
