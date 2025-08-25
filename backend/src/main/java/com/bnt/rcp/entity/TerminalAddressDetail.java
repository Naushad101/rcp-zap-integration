package com.bnt.rcp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "terminal_address_detail")
public class TerminalAddressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private Integer country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "official_email")
    private String email;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }
    
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    
    public Integer getCountry() { return country; }
    public void setCountry(Integer country) { this.country = country; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
}
