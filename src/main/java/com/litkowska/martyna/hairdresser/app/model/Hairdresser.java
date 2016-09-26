package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class Hairdresser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private HairdresserLevel hairdresserLevel;
//    private List<HairdresserTag> hairdresserTagList;

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

//    public List<HairdresserTag> getHairdresserTagList() {
//        return hairdresserTagList;
//    }

//    public void setHairdresserTagList(final List<HairdresserTag> hairdresserTagList) {
//        this.hairdresserTagList = hairdresserTagList;
//    }
}
