package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;

/**
 * Created by Martyna on 29.10.2016.
 */
public class HairdresserDTO {
    private long id;
    private String lastName;
    private String firstName;

    public HairdresserDTO() {
    }

    public HairdresserDTO(final Hairdresser hairdresser){
        this.id = hairdresser.getId();
        this.lastName = hairdresser.getLastName();
        this.firstName = hairdresser.getFirstName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
