package com.bnt.rcp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "location_detail")
public class LocationDetail extends AuditEntity {
    
    @Override
    public String toString() {
        return "LocationDetail [id=" + id + ", address1=" + address1 + ", address2=" + address2
                + ", city=" + city + ", countryState=" + countryState + ", zip=" + zip + ", country=" + country
                + ", phone=" + phone + ", fax=" + fax + ", website=" + website + ", email=" + email + ", location="
                + location + "]";
    }

  
    @Id
    private Integer id;


    @Column(name = "address_1")
    private String address1;

 
    @Column(name = "address_2")
    private String address2;


    @Column(name = "city")
    private String city;

  
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
    private CountryState countryState;

  
    @Column(name = "postal_code")
    private String zip;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    private Country country;


    @Column(name = "phone")
    @Size(max = 20)
    private String phone;

  
    @Column(name = "fax")
    private String fax;

 
    @Column(name = "website")
    private String website;


    @Column(name = "official_email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Location location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryState getCountryState() {
        return countryState;
    }

    public void setCountryState(CountryState countryState) {
        this.countryState = countryState;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}