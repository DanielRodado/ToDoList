package com.mindhub.todolist.exceptions.userExceptions;

public class NotFoundUserEntityException extends RuntimeException {

    public NotFoundUserEntityException(String message) {
        super(message);
    }

}
