package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.configurations.JwtUtils;
import com.mindhub.todolist.dto.LoginUser;
import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.AuthService;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.mindhub.todolist.mappers.UserEntityMapper.userEntityToUserEntityDTO;

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

    @Override
    public ResponseEntity<UserEntityDTO> requestCreateUserEntity(UserEntityApplicationDTO userApp) {
        return userEntityService.requestCreateUserEntity(userApp);
    }

    @Override
    public void validateUserEntityApplication(UserEntityApplicationDTO userApp) {
        userEntityService.validateUserEntityApplication(userApp);
    }

    @Override
    public UserEntity buildUserEntityFromDTO(UserEntityApplicationDTO userApp) {
        return userEntityService.buildUserEntityFromDTO(userApp);
    }

    @Override
    public ResponseEntity<UserEntityDTO> buildResponseEntity(UserEntityDTO userEntityDTO, HttpStatus httpStatus) {
        return userEntityService.buildResponseEntity(userEntityDTO, httpStatus);
    }

    @Override
    public void saveUserEntity(UserEntity userEntity) {
        userEntityService.saveUserEntity(userEntity);
    }


}
