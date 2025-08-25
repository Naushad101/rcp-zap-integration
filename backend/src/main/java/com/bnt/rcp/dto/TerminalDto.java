package com.bnt.rcp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TerminalDto extends BaseDto {
    @JsonProperty("terminalId")
    private String terminalId;

    // @JsonProperty("name")
    // private String name;

    // @JsonProperty("terminalCode")
    // private String code;

    @JsonProperty("macaddress")
    private String macAddress;

    @JsonProperty("eppserialnumber")
    private String eppSerialNumber;

    @JsonProperty("description")
    private String description;

    @JsonProperty("institution")
    private GenericWrapper institution;

    @JsonProperty("institutionGroup")
    private GenericWrapper institutionGroup;

    @JsonProperty("location")
    private GenericWrapper location;

    // @JsonProperty("type")
    // private GenericWrapper deviceType;

    @JsonProperty("terminalModel")
    private GenericWrapper terminalModel;

    @JsonProperty("detail")
    private TerminalAddressDetailDto addressDetail;

    // @JsonProperty("option")
    // private TerminalOptionDto atmOption;

    @JsonProperty("rhJFlag")
    private Boolean rhJFlag;

    @JsonProperty("locked")
    private Character locked;

    // @JsonProperty("terminalTag")
    // private List<TerminalTagDto> terminalTag;

    @JsonProperty("locationInherit")
    private boolean locationInherit;

    @JsonProperty("branchSortCode")
    private Integer branchSortCode;

    // @JsonProperty("additionalAttribute")
    // private String additionalAttribute;

    // Getters and setters
    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }
    
    // public String getName() { return name; }
    // public void setName(String name) { this.name = name; }
    
    // Add getters/setters for all other fields
}
