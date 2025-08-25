package com.bnt.rcp.exception;

import lombok.Getter;

@Getter
public class RippsAdminRestException extends RuntimeException {

    private final String status;
    private final String message;
    private final transient Object data;

    public RippsAdminRestException(String status, String message, Object data) {
        super(message);

        this.status = status;
        this.message = message;
        this.data = data;
    }
    
}
