package com.bnt.rcp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalModelDto extends BaseDto {
    private TerminalTypeDto type;
    private TerminalVendorDto vendor;
    private TerminalSitingDto siting;
    private String modelname;
    private Integer status;
    private Integer deleted;

}
