package com.bnt.rcp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalTypeDto extends BaseDto {
    private String type;
    private Integer status;
    private Integer deleted;

}
