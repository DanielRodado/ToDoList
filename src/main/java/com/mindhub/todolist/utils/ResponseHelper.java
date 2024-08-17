package com.mindhub.todolist.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseHelper {

    public static ResponseEntity<String> buildResponse(String text, HttpStatus httpStatus) {
        return new ResponseEntity<>(text, httpStatus);
    }

    public static ResponseEntity<?> buildResponseEntity(Object object, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(object);
    }


}
