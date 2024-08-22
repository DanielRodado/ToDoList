package com.mindhub.todolist.serviceTest;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.dto.UserApplicationDTO;
import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Autowired
    private TaskService taskService;

    @Test
    public void testCreateTask() {
        String title = "New Task";
        String description = "Description of new task";
        TaskStatus status = TaskStatus.PENDING;
        Long userId = 1L;

        UserEntity mockUser = new UserEntity("melba", "melba@gmail.com", "1234567890");
        when(userEntityRepository.save(any(UserEntity.class))).thenReturn(mockUser);
        when(userEntityRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userEntityRepository.existsById(anyLong())).thenReturn(true);
        userEntityRepository.save(mockUser);


        Task mockTask = new Task(title, description, status);
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        TaskApplicationDTO taskAppDTO = new TaskApplicationDTO(title, description, status.toString(), new UserApplicationDTO(userId));
        ResponseEntity<TaskDTO> response = taskService.requestCreateTask(taskAppDTO);


        verify(taskRepository).save(any(Task.class)); // Verifica que el repositorio haya sido llamado con cualquier// Task
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Verifica que el status sea 201 Created
        assertEquals(title, response.getBody().getTitle()); // Verifica que el título es correcto
        assertEquals(description, response.getBody().getDescription()); // Verifica que la descripción es correcta
        assertEquals(status, response.getBody().getTaskStatus());  // Verifica que el estado es correcto
    }


}
