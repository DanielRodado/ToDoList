package com.mindhub.todolist.services;

import com.mindhub.todolist.models.UserEntity;

public interface UserEntityService {

    boolean existsUserEntityById(Long id);

    void validateUserById(Long id);

    void saveUserEntity(UserEntity userEntity);

}
