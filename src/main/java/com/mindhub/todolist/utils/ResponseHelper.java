package com.mindhub.todolist.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseHelper {

    public static ResponseEntity<String> createResponse(String text, HttpStatus httpStatus) {
        return new ResponseEntity<>(text, httpStatus);
    }

    public static ResponseEntity<?> createResponseOk(Object object) {
        return ResponseEntity.ok(object);
    }
}
