package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;

/**
 * Created by Martyna on 26.09.2016.
 */
@Entity
public class HairdresserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private int level;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "HairdresserLevel{" +
                "id=" + id +
                ", level=" + level +
                '}';
    }
}
