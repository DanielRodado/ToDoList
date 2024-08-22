package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.LoginUser;
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



}
