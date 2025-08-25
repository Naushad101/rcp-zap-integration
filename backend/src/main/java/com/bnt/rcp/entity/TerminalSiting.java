package com.bnt.rcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="terminal_siting")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalSiting extends BaseEntity {
    private static final long serialVersionUID = -47388657913298266L;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="status")
    private Integer status;

    @Column(name = "deleted", nullable = true)
    private Integer deleted;

}
