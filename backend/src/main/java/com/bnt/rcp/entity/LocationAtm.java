package com.bnt.rcp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "location_atm")
public class LocationAtm extends BaseEntity {

    @Column(name = "region")
    private Integer region;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "locked")
    private Character locked;

    @Column(name = "sublocation")
    private Boolean sublocation;

    @Column(name = "sublocationid")
    private Integer parentLocationId;

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Character getDeleted() {
        return deleted;
    }

    public void setDeleted(Character deleted) {
        this.deleted = deleted;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }

    public Boolean getSublocation() {
        return sublocation;
    }

    public void setSublocation(Boolean sublocation) {
        this.sublocation = sublocation;
    }

    public Integer getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(Integer parentLocationId) {
        this.parentLocationId = parentLocationId;
    }
}