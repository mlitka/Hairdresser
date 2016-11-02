package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Hairdresser extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private HairdresserLevel hairdresserLevel;
    @OneToOne
    @NotNull
    private Shift shift;
    @OneToMany
    private List<HairdresserTag> hairdresserTagList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public HairdresserLevel getHairdresserLevel() {
        return hairdresserLevel;
    }

    public void setHairdresserLevel(final HairdresserLevel hairdresserLevel) {
        this.hairdresserLevel = hairdresserLevel;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public List<HairdresserTag> getHairdresserTagList() {
        return hairdresserTagList;
    }

    public void setHairdresserTagList(final List<HairdresserTag> hairdresserTagList) {
        this.hairdresserTagList = hairdresserTagList;
    }

    @Override
    public String toString() {
        return "Hairdresser{" +
                "id=" + id +
                ", hairdresserLevel=" + hairdresserLevel +
                ", shift=" + shift +
                ", hairdresserTagList=" + hairdresserTagList +
                '}';
    }
}
