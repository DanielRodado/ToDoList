package com.mindhub.todolist.exceptions.taskExceptions;

public class TaskNotBelongToUserException extends RuntimeException {

    public TaskNotBelongToUserException(String message) {
        super(message);
    }

}
