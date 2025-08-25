package com.bnt.rcp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ResponseEntityData {

    private String status;

    private String message;
    
    private Object data;

    public ResponseEntityData(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
