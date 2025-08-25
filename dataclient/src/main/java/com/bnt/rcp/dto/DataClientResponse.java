package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class DataClientResponse {

    private String message;

    private DatabaseSyncUpDto data;

}
