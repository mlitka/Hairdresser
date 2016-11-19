package com.litkowska.martyna.hairdresser.app.repository;

/**
 * Created by Martyna on 19.11.2016.
 */
public class UpgradeHairdresserDTO {
    private String username;
    private String shiftStart;
    private String shiftEnd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }
}
