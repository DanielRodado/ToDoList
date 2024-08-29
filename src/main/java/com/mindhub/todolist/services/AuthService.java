package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.LoginUser;
import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface AuthService {

    // Authentication methods

    ResponseEntity<String> authenticateUser(LoginUser loginUser);

    Authentication getCurrentUser(LoginUser loginUser);

    UsernamePasswordAuthenticationToken authenticate(LoginUser loginUser);

    void setSecurityContextHolder(Authentication authentication);

    String generateJwtToken(String username);

    // Create New UserEntity

    ResponseEntity<UserEntityDTO> requestCreateUserEntity(UserEntityApplicationDTO userApp);

    void validateUserEntityApplication(UserEntityApplicationDTO userApp);

    UserEntity buildUserEntityFromDTO(UserEntityApplicationDTO userApp);

    ResponseEntity<UserEntityDTO> buildResponseEntity(UserEntityDTO userEntityDTO, HttpStatus httpStatus);

    void saveUserEntity(UserEntity userEntity);


}
