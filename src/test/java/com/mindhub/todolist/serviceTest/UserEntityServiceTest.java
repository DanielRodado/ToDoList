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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserEntityServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserEntityService userEntityService;

    @Test
    public void testGetUserByUsername() {
        UserEntity userEntity = new UserEntity("melba", "melba@gmail.com", "123");
        when(userEntityRepository.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

        UserEntity result = userEntityService.findUserEntityByUsername("melba");

        assertEquals("melba", result.getUsername());
    }

}
