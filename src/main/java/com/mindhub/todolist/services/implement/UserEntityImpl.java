package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.exceptions.taskExceptions.InvalidFieldInputTaskException;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public boolean existsUserEntityById(Long id) {
        return userEntityRepository.existsById(id);
    }

    @Override
    public void validateUserById(Long id) {
        if (!existsUserEntityById(id)) {
            throw new NotFoundUserEntityException("User not found");
        }
    }

    @Override
    public void saveUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }
}
