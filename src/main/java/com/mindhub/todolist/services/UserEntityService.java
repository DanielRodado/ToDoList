package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface UserEntityService {

    // Method Repository

    List<UserEntity> getAllUserEntity();

    UserEntity findUserEntityById(Long id);

    UserEntity findUserEntityByUsername(String username);

    boolean existsUserEntityById(Long id);

    boolean existsUserEntityByEmail(String email);

    // Methods Controller

    Set<UserEntityDTO> getAllUserEntityDTO();

    UserEntityDTO findUserEntityDTOById(Long id);

    UserEntityDTO findUserEntityDTOByUsername(String username);

    void validateUserById(Long id);

    void addTaskToUserEntityById(Task task, Long userId);

    // Create New UserEntity
    ResponseEntity<UserEntityDTO> requestCreateUserEntity(UserEntityApplicationDTO userApp);

    void validateUserEntityApplication(UserEntityApplicationDTO userApp);

    void validateUserEntityUsername(String username);

    void validateUserEntityEmail(String email);

    void validateExistsUserEntityByEmail(String email);

    void validateUserEntityPassword(String password);

    UserEntity buildUserEntityFromDTO(UserEntityApplicationDTO userApp);

    UserEntityDTO transformToUserEntityDTO(UserEntity userEntity);

    ResponseEntity<UserEntityDTO> buildResponseEntity(UserEntityDTO userEntityDTO, HttpStatus httpStatus);

    void saveUserEntity(UserEntity userEntity);

}
