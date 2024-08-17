package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void saveUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }
}
