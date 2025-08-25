package com.bnt.rcp.dto;

import java.sql.Timestamp;

import lombok.*;

@Setter 
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {

    private int id;

    private int createdBy;

    private int updatedBy;

    private Timestamp createdOn;

    private Timestamp updatedOn;

}
