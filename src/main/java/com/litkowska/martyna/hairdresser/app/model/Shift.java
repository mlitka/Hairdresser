package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Created by Martyna on 07.10.2016.
 */
@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @NotNull
    private LocalTime shiftStart;
    @Column
    @NotNull
    private LocalTime shiftEnd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                '}';
    }
}
