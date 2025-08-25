package com.bnt.rcp.databasesyncup.dto;

import java.sql.Timestamp;

import lombok.*;

@Setter
@Getter
public class BaseDto {

    private int id;

    private int createdBy;

    private int updatedBy;

    private Timestamp createdOn;

    private Timestamp updatedOn;

}
