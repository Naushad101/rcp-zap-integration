package com.bnt.rcp.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "terminal")
public class Terminal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "terminalid")
    private String terminalId;

    // @Column(name = "name")
    // private String name;

    // @Column(name = "terminal_code")
    // private String code;

    @Column(name = "macaddress")
    private String macAddress;

    @Column(name = "eppserialnumber")
    private String eppSerialNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "institution_group")
    private Integer institutionGroup;

    @Column(name = "rkiflag")
    private Boolean rkIFlag;

    @Column(name = "deleted")
    private Character deleted;

    @Column(name = "inheritaddressfromlocation")
    private Boolean inheritAddress;

    @Column(name = "branch_sortcode")
    private Integer branchSortCode;

    // @Column(name = "additional_attribute")
    // private String additionalAttribute;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "terminal_model_id", referencedColumnName = "id")
    private TerminalModel terminalModel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_detail_id", referencedColumnName = "id")
    private TerminalAddressDetail addressDetail;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "atm_option_id", referencedColumnName = "id")
    // private TerminalOption atmOption;

    // @OneToOne
    // @MapsId
    // @JoinColumn(name = "device_id")
    // private Device device;

    // Getters and setters
    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }
    
    // public String getName() { return name; }
    // public void setName(String name) { this.name = name; }
    
    // public String getCode() { return code; }
    // public void setCode(String code) { this.code = code; }
    
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    
    public String getEppSerialNumber() { return eppSerialNumber; }
    public void setEppSerialNumber(String eppSerialNumber) { this.eppSerialNumber = eppSerialNumber; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getInstitutionGroup() { return institutionGroup; }
    public void setInstitutionGroup(Integer institutionGroup) { this.institutionGroup = institutionGroup; }
    
    public Boolean getRkIFlag() { return rkIFlag; }
    public void setRkIFlag(Boolean rkIFlag) { this.rkIFlag = rkIFlag; }
    
    public Character getDeleted() { return deleted; }
    public void setDeleted(Character deleted) { this.deleted = deleted; }
    
    public Boolean getInheritAddress() { return inheritAddress; }
    public void setInheritAddress(Boolean inheritAddress) { this.inheritAddress = inheritAddress; }
    
    public Integer getBranchSortCode() { return branchSortCode; }
    public void setBranchSortCode(Integer branchSortCode) { this.branchSortCode = branchSortCode; }
    
    // public String getAdditionalAttribute() { return additionalAttribute; }
    // public void setAdditionalAttribute(String additionalAttribute) { this.additionalAttribute = additionalAttribute; }
    
    public TerminalModel getTerminalModel() { return terminalModel; }
    public void setTerminalModel(TerminalModel terminalModel) { this.terminalModel = terminalModel; }
    
    public TerminalAddressDetail getAddressDetail() { return addressDetail; }
    public void setAddressDetail(TerminalAddressDetail addressDetail) { this.addressDetail = addressDetail; }
    
    // public TerminalOption getAtmOption() { return atmOption; }
    // public void setAtmOption(TerminalOption atmOption) { this.atmOption = atmOption; }
    
    // public Device getDevice() { return device; }
    // public void setDevice(Device device) { this.device = device; }
}
