package com.mindhub.todolist.RepositoryTest;

import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveTaskTest() {
        Task task = new Task("Test Task", "Test Description", TaskStatus.PENDING);
        Task savedTask = taskRepository.save(task);

         // Verifica que la tarea se haya guardado correctamente
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());
        assertTrue(foundTask.isPresent());

        assertEquals("Test Task", foundTask.get().getTitle());
        assertEquals("Test Description", foundTask.get().getDescription());
        assertEquals(TaskStatus.PENDING, foundTask.get().getTaskStatus());
    }

}
