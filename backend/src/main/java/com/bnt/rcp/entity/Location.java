package com.bnt.rcp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.sql.Timestamp;

import org.hibernate.annotations.Formula;


@Entity
@Table(name = "location")
public class Location extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id", nullable = true)
    private Merchant merchant;

    @Column(name = "code")
    private String code;


    @Column(name = "name")
    private String name;

   
    @Column(name = "description")
    private String description;


    @Column(name = "activated_on")
    private Timestamp activatedOn;

    @Column(name = "expiry_on")
    private Timestamp expiryOn;

   
    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "locked")
    private Character locked;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "location", orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private LocationDetail locationDetail;

    // @Column(name = "store_id")
    // private String storeId;

    @Column(name = "pos_safty_flag")
    private Character posSafetyFlag;

    @Column(name = "reversal_timeout")
    private String reversalTimeout;

    @Column(name = "additional_attribute")
    private String additionalAttribute;

    // @JsonIgnore
    // @Column(name = "hostcapture")
    // private boolean hostcapture;

    @Column(name = "latitude", nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    // public Integer getTotalDevice() {
    //     return totalDevice;
    // }

    // public void setTotalDevice(Integer totalDevice) {
    //     this.totalDevice = totalDevice;
    // }

    public Character getPosSafetyFlag() {
        return posSafetyFlag;
    }

    public void setPosSafetyFlag(Character posSafetyFlag) {
        this.posSafetyFlag = posSafetyFlag;
    }

    public String getReversalTimeout() {
        return reversalTimeout;
    }

    public void setReversalTimeout(String reversalTimeout) {
        this.reversalTimeout = reversalTimeout;
    }

    public String getAdditionalAttribute() {
        return additionalAttribute;
    }

    public void setAdditionalAttribute(String additionalAttribute) {
        this.additionalAttribute = additionalAttribute;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getActivateOn() {
        return activatedOn;
    }

    public void setActivateOn(Timestamp activateOn) {
        this.activatedOn = activateOn;
    }

    public Timestamp getExpiryOn() {
        return expiryOn;
    }

    public void setExpiryOn(Timestamp expiryOn) {
        this.expiryOn = expiryOn;
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

    public LocationDetail getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(LocationDetail locationDetail) {
        this.locationDetail = locationDetail;
    }

    // public String getStoreId() {
    //     return storeId;
    // }

    // public void setStoreId(String storeId) {
    //     this.storeId = storeId;
    // }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // public Integer getTotalDevice() {
    //     return totalDevice;
    // }

    // public void setTotalDevice(Integer totalDevice) {
    //     this.totalDevice = totalDevice;
    // }
}
