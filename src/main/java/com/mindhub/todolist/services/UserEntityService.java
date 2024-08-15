package com.mindhub.todolist.services;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;

public interface UserEntityService {

    UserEntity findUserEntityById(Long id);

    boolean existsUserEntityById(Long id);

    void validateUserById(Long id);

    void addTaskToUserEntityById(Task task, Long userId);

    void saveUserEntity(UserEntity userEntity);

}
