package com.bnt.rcp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bnt.rcp.dto.ResponseEntityData;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RippsAdminRestException.class)
    public ResponseEntity<ResponseEntityData> handleCommonException(RippsAdminRestException ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        ResponseEntityData error = new ResponseEntityData(ex.getStatus(), ex.getMessage(), ex.getData());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}
