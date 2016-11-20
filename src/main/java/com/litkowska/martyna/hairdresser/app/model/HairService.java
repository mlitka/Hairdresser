package com.litkowska.martyna.hairdresser.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Martyna on 21.09.2016.
 */
@Entity
public class HairService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
//    duration in minutes needed to to the service
    private int duration;
    @Column
    private String priceRange;
    /**
     * used to all available hairservices in the salon
     */
    @Column
    private boolean isHidden;

    public HairService() {
        isHidden = false;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Override
    public String toString() {
        return "HairService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", priceRange='" + priceRange + '\'' +
                ", isHidden=" + isHidden +
                '}';
    }
}
