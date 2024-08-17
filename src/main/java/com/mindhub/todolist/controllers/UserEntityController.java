package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping
    public Set<UserEntityDTO> getAllUsersDTO() {
        return userEntityService.getAllUserEntityDTO();
    }

    @GetMapping("/{userEntityId}")
    public UserEntityDTO getUserDTOById(@PathVariable("userEntityId") Long userEntityId) {
        return userEntityService.findUserEntityDTOById(userEntityId);
    }

}
