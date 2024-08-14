package com.mindhub.todolist.dto;

import com.mindhub.todolist.models.UserEntity;

public class UserEntityDTO {

    private final Long id;

    private final String username, email;

    public UserEntityDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
