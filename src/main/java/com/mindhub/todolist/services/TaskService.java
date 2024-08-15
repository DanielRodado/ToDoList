package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface TaskService {

    // Methods

    Task getTaskById(Long id);

    TaskDTO getTaskDTOById(Long id);

    TaskDTO transformToTaskDTO(Task task);

    List<Task> getAllTasks();

    Set<TaskDTO> getAllTasksDTO();

    ResponseEntity<TaskDTO> requestGetTaskDTOById(Long id);

    // Create new Task
    ResponseEntity<TaskDTO> requestCreateTask(TaskApplicationDTO taskApp);

    void validateTaskApplication(TaskApplicationDTO taskApp);

    void validateTaskTitle(String title);

    void validateTaskDescription(String description);

    void validateTaskStatus(String taskStatus);

    Task buildTaskFromDTO(TaskApplicationDTO taskApp);

    void associateTaskWithUser(Task task, Long userId);

    ResponseEntity<TaskDTO> buildResponseEntity(TaskDTO taskDTO, HttpStatus httpStatus);

    void saveTask(Task task);

}
