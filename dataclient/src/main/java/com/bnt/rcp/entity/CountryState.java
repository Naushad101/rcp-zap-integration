package com.bnt.rcp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "country_state")
public class CountryState extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "display_name")
    private String stateName;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @Column(name = "active")
    private Character active;

}

