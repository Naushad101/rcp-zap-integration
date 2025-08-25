package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor 
public class GenericWrapper {

    private Integer id;

    private String name;

    private String code;
    
    private Character active;
}
