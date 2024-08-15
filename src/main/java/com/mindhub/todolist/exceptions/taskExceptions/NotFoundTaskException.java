package com.mindhub.todolist.exceptions.taskExceptions;

public class NotFoundTaskException extends RuntimeException {

    public NotFoundTaskException(String message) {
        super(message);
    }

}
