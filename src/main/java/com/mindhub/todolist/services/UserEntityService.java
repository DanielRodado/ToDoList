package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;
import java.util.Set;

public interface UserEntityService {

    // Method Repository

    List<UserEntity> getAllUserEntity();

    UserEntity findUserEntityById(Long id);

    boolean existsUserEntityById(Long id);

    // Methods Controller

    Set<UserEntityDTO> getAllUserEntityDTO();

    UserEntityDTO findUserEntityDTOById(Long id);

    void validateUserById(Long id);

    void addTaskToUserEntityById(Task task, Long userId);

    void saveUserEntity(UserEntity userEntity);

}
