package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.Hairdresser;

import java.time.LocalTime;

/**
 * Created by Martyna on 29.10.2016.
 */
public class HairdresserDTO {
    private long id;
    private String lastName;
    private String firstName;
    private String email;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;

    public HairdresserDTO() {
    }

    public HairdresserDTO(final Hairdresser hairdresser){
        this.id = hairdresser.getHairdresserId();
        this.lastName = hairdresser.getUser().getLastName();
        this.firstName = hairdresser.getUser().getFirstName();
        this.email = hairdresser.getUser().getEmail();
        this.shiftStart = hairdresser.getShift().getShiftStart();
        this.shiftEnd = hairdresser.getShift().getShiftEnd();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }
}
