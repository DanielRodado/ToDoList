package com.mindhub.todolist.exceptions;

import com.mindhub.todolist.exceptions.taskExceptions.InvalidFieldInputTaskException;
import com.mindhub.todolist.exceptions.taskExceptions.InvalidTaskStatusException;
import com.mindhub.todolist.exceptions.taskExceptions.NotFoundTaskException;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundUserEntityException.class)
    public ResponseEntity<String> handleNotFoundUserEntityException(NotFoundUserEntityException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundTaskException.class)
    public ResponseEntity<String> handleNotFoundTaskException(NotFoundTaskException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFieldInputTaskException.class)
    public ResponseEntity<String> handleInvalidFieldInputTaskException(InvalidFieldInputTaskException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    public ResponseEntity<String> handleInvalidTaskStatusException(InvalidTaskStatusException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
