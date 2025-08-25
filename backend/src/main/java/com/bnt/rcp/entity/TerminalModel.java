package com.bnt.rcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="terminal_model")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalModel extends BaseEntity {
    private static final long serialVersionUID = -250193666891666L;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private TerminalTypeEntity type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor")
    private TerminalVendor vendor;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "siting")
    private TerminalSiting siting;

    @Column(name="modelname")
    private String modelname;

    @Column(name="status")
    private Integer status;

    @Column(name = "deleted")
    private Integer deleted;

}
