package com.bnt.rcp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalVendorDto extends BaseDto {
    private String name;
    private Integer status;
    private Integer deleted;
}
