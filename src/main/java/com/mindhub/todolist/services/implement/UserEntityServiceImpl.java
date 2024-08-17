package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.exceptions.userExceptions.EmailAlreadyExistsException;
import com.mindhub.todolist.exceptions.userExceptions.InvalidFieldInputUserEntityException;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    // Method Repository

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public List<UserEntity> getAllUserEntity() {
        return userEntityRepository.findAll();
    }

    @Override
    public UserEntity findUserEntityById(Long id) {
        return userEntityRepository.findById(id).orElseThrow(() -> new NotFoundUserEntityException("User not found."));
    }

    @Override
    public boolean existsUserEntityById(Long id) {
        return userEntityRepository.existsById(id);
    }

    @Override
    public boolean existsUserEntityByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    // Methods Controller

    @Override
    public Set<UserEntityDTO> getAllUserEntityDTO() {
        return getAllUserEntity().stream().map(UserEntityDTO::new).collect(Collectors.toSet());
    }

    @Override
    public UserEntityDTO findUserEntityDTOById(Long id) {
        return new UserEntityDTO(findUserEntityById(id));
    }

    @Override
    public void validateUserById(Long id) {
        if (!existsUserEntityById(id)) {
            throw new NotFoundUserEntityException("User not found.");
        }
    }

    @Override
    public void addTaskToUserEntityById(Task task, Long userId) {
        UserEntity userEntity = findUserEntityById(userId);
        task.setUserEntity(userEntity);
    }

    // Create New UserEntity
    @Override
    public ResponseEntity<UserEntityDTO> requestCreateUserEntity(UserEntityApplicationDTO userApp) {
        validateUserEntityApplication(userApp);
        UserEntity userEntity = buildUserEntityFromDTO(userApp);
        saveUserEntity(userEntity);
        return buildResponseEntity(transformToUserEntityDTO(userEntity), HttpStatus.CREATED);
    }

    @Override
    public void validateUserEntityApplication(UserEntityApplicationDTO userApp) {
        validateUserEntityEmail(userApp.email());
        validateExistsUserEntityByEmail(userApp.email());
        validateUserEntityPassword(userApp.password());
        validateUserEntityUsername(userApp.username());
    }

    @Override
    public void validateUserEntityUsername(String username) {
        if (username.isBlank()) {
            throw new InvalidFieldInputUserEntityException("The username cannot be empty or have blank spaces.");
        }
    }

    @Override
    public void validateUserEntityEmail(String email) {
        if (email.isBlank()) {
            throw new InvalidFieldInputUserEntityException("The email cannot be empty or have blank spaces.");
        }
    }

    @Override
    public void validateExistsUserEntityByEmail(String email) {
        if (existsUserEntityByEmail(email)) {
            throw new EmailAlreadyExistsException("Unable to assign that email (" + email + "), try another one.");
        }
    }

    @Override
    public void validateUserEntityPassword(String password) {
        if (password.isBlank()) {
            throw new InvalidFieldInputUserEntityException("The password cannot be empty or have blank spaces.");
        }
    }

    @Override
    public UserEntity buildUserEntityFromDTO(UserEntityApplicationDTO userApp) {
        return new UserEntity(userApp.username(), userApp.email(), userApp.password());
    }

    @Override
    public UserEntityDTO transformToUserEntityDTO(UserEntity userEntity) {
        return new UserEntityDTO(userEntity);
    }

    @Override
    public ResponseEntity<UserEntityDTO> buildResponseEntity(UserEntityDTO userEntityDTO, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(userEntityDTO);
    }

    @Override
    public void saveUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }
}
