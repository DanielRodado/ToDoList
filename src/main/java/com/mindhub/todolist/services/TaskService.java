package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.models.Task;
import org.springframework.http.ResponseEntity;

public interface TaskService {

    // Methods

    Task getTaskById(Long id);

    TaskDTO getTaskDTOById(Long id);

    ResponseEntity<TaskDTO> requestGetTaskDTOById(Long id);

    // Create new Task
    ResponseEntity<String> requestCreateTask(TaskApplicationDTO taskApp);

    void validateTaskApp(TaskApplicationDTO taskApp);

    void validateTaskTitle(String title);

    void validateTaskDescription(String description);

    void validateTaskStatus(String taskStatus);

    Task createTask(TaskApplicationDTO taskApp);

    void saveTask(Task task);

}
