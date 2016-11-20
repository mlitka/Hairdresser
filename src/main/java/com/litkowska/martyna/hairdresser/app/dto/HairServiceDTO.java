package com.litkowska.martyna.hairdresser.app.dto;

import com.litkowska.martyna.hairdresser.app.model.HairService;

/**
 * Created by Martyna on 19.11.2016.
 */
public class HairServiceDTO {
    private long id;
    private String name;
    private int duration = -1;
    private String priceRange;
    private boolean isHidden;

    public HairServiceDTO() {
    }

    public HairServiceDTO(final HairService hairService) {
        id = hairService.getId();
        name = hairService.getName();
        duration = hairService.getDuration();
        priceRange = hairService.getPriceRange() != null ? hairService.getPriceRange() : "";
        isHidden = hairService.isHidden();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
}
