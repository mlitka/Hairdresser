package com.litkowska.martyna.hairdresser.app.dto;

/**
 * Created by Martyna on 01.11.2016.
 */
public class VisitDTO {
    private ClientDTO client;
    private String date;
    private long hairServiceId;
    private String time;
    private long hairdresserId;


    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getHairServiceId() {
        return hairServiceId;
    }

    public void setHairServiceId(long hairServiceId) {
        this.hairServiceId = hairServiceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(long hairdresserId) {
        this.hairdresserId = hairdresserId;
    }
}
