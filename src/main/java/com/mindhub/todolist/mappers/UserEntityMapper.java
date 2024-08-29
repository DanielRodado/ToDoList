package com.mindhub.todolist.mappers;

import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityMapper {

    // to DTO

    public static UserEntityDTO userEntityToUserEntityDTO(UserEntity userEntity) {
        return new UserEntityDTO(userEntity);
    }

    public static Set<UserEntityDTO> userEntitiesToUserEntityDTOs(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserEntityMapper::userEntityToUserEntityDTO).collect(Collectors.toSet());
    }

    // to Entity

    public static UserEntity userEntityDTOToUserEntity(UserEntityApplicationDTO userApp) {
        return new UserEntity(userApp.username(), userApp.email(), userApp.password());
    }

}
