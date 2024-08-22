package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.configurations.JwtUtils;
import com.mindhub.todolist.dto.LoginUser;
import com.mindhub.todolist.services.AuthService;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserEntityService userEntityService;

    // Authentication methods

    @Override
    public ResponseEntity<String> authenticateUser(LoginUser loginUser) {
        Authentication authentication = getCurrentUser(loginUser);
        setSecurityContextHolder(authentication);
        String jwt = generateJwtToken(authentication.getName());
        return ResponseEntity.ok(jwt);
    }

    @Override
    public Authentication getCurrentUser(LoginUser loginUser) {
        return authenticationManager.authenticate(authenticate(loginUser));
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(LoginUser loginUser) {
        return new UsernamePasswordAuthenticationToken(loginUser.username(), loginUser.password());
    }

    @Override
    public void setSecurityContextHolder(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String generateJwtToken(String username) {
        return jwtUtils.generateToken(username);
    }
}
