package com.mindhub.todolist.exceptions.taskExceptions;

public class InvalidTaskStatusException extends RuntimeException {

    public InvalidTaskStatusException(String message) {
        super(message);
    }

}
