package com.bnt.rcp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalSitingDto extends BaseDto {
    private String name;
    private Integer status;
    private Integer deleted=0;

}
