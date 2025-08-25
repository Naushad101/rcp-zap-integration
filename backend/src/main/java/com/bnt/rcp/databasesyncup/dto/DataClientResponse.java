package com.bnt.rcp.databasesyncup.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataClientResponse {

    private String message;

    private DatabaseSyncUpDto data;

}
