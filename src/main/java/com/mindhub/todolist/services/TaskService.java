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

    void validateTaskApplication(TaskApplicationDTO taskApp);

    void validateTaskTitle(String title);

    void validateTaskDescription(String description);

    void validateTaskStatus(String taskStatus);

    Task buildTaskFromDTO(TaskApplicationDTO taskApp);

    void associateTaskWithUser(Task task, Long userId);

    void saveTask(Task task);

}
