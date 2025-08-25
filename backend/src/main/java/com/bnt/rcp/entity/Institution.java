package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institution")
public class Institution extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "activate_on")
    private Timestamp activateOn;

    @Column(name = "expiry_on")
    private Timestamp expiryOn;

    @Column(name = "locked")
    private Character locked;

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "status")
    private String status;

}
