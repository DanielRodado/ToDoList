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
import static org.mockito.ArgumentMatchers.anyLong;
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
    public void testGetUserByUsername() {
        UserEntity mokUuserEntity = new UserEntity("melba", "melba@gmail.com", "1234567890");
        when(userEntityRepository.findById(anyLong())).thenReturn(Optional.of(mokUuserEntity));

        UserEntity result = userEntityService.findUserEntityById(1L);

        verify(userEntityRepository).findById(anyLong()); // Verifica que el repositorio haya sido llamado con cualquier ID
        assertEquals("melba", result.getUsername()); // Verifica que el username sea correcto
    }

}
