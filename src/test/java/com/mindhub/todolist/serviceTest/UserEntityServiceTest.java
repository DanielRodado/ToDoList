package com.mindhub.todolist.serviceTest;

import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserEntityServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserEntityService userEntityService;

    @Test
    public void findUserEntityById_whenIdIsValid_shouldReturnUserEntity() {
        UserEntity mokUserEntity = new UserEntity("melba", "melba@gmail.com", "1234567890");
        when(userEntityRepository.findById(anyLong())).thenReturn(Optional.of(mokUserEntity));

        UserEntity result = userEntityService.findUserEntityById(1L);

        verify(userEntityRepository).findById(anyLong());
        assertEquals("melba", result.getUsername());
    }

    @Test
    public void registerUser_thenUserIsFound() {
        UserEntity newUser = new UserEntity("testUser", "testuser@example.com", "testPassword");
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(newUser);
        when(userEntityRepository.findByUsername(anyString())).thenReturn(Optional.of(newUser));
        userEntityService.saveUserEntity(newUser);

        UserEntity foundUser = userEntityService.findUserEntityByUsername("testUser");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("testuser@example.com");
    }

}
