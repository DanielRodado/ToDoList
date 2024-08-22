package com.mindhub.todolist.RepositoryTest;

import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserEntityRepositoryTest {

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void registerUser() {
        UserEntity newUser = new UserEntity("testuser", "testuser@example.com", "testpassword");
        userEntityService.saveUserEntity(newUser);

        UserEntity foundUser = userEntityRepository.findByUsername("testuser").orElse(null);

        // Verificar que el usuario fue guardado correctamente
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("testuser@example.com");
    }

}
