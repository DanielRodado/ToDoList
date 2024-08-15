package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.exceptions.taskExceptions.InvalidFieldInputTaskException;
import com.mindhub.todolist.exceptions.taskExceptions.InvalidTaskStatusException;
import com.mindhub.todolist.exceptions.taskExceptions.NotFoundTaskException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.todolist.utils.ResponseHelper.buildResponse;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserEntityService userEntityService;

    // Methods

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Task not found."));
    }

    @Override
    public TaskDTO getTaskDTOById(Long id) {
        return new TaskDTO(getTaskById(id));
    }

    @Override
    public TaskDTO transformToTaskDTO(Task task) {
        return new TaskDTO(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Set<TaskDTO> getAllTasksDTO() {
        return getAllTasks().stream().map(TaskDTO::new).collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<TaskDTO> requestGetTaskDTOById(Long id) {
        return ResponseEntity.ok(getTaskDTOById(id));
    }

    // Create new Task
    @Override
    public ResponseEntity<TaskDTO> requestCreateTask(TaskApplicationDTO taskApp) {
        validateTaskApplication(taskApp);
        Task task = buildTaskFromDTO(taskApp);
        associateTaskWithUser(task, taskApp.user().id());
        saveTask(task);
        return buildResponseEntity(transformToTaskDTO(task), HttpStatus.CREATED);
    }

    @Override
    public void validateTaskApplication(TaskApplicationDTO taskApp) {
        userEntityService.validateUserById(taskApp.user().id());
        validateTaskTitle(taskApp.title());
        validateTaskDescription(taskApp.description());
        validateTaskStatus(taskApp.taskStatus());
    }

    @Override
    public void validateTaskTitle(String title) {
        if (title.isBlank()) {
            throw new InvalidFieldInputTaskException("The title cannot be empty or have blank spaces.");
        }
    }

    @Override
    public void validateTaskDescription(String description) {
        if (description.isBlank()) {
            throw new InvalidFieldInputTaskException("The description cannot be empty or have blank spaces.");
        }
    }

    @Override
    public void validateTaskStatus(String taskStatus) {
        try {
            TaskStatus status = TaskStatus.valueOf(taskStatus);
        } catch (Exception none) {
            throw new InvalidTaskStatusException("Invalid task status: " + taskStatus);
        }
    }

    @Override
    public Task buildTaskFromDTO(TaskApplicationDTO taskApp) {
        return new Task(taskApp.title(), taskApp.description(), TaskStatus.valueOf(taskApp.taskStatus()));
    }

    @Override
    public void associateTaskWithUser(Task task, Long userId) {
        userEntityService.addTaskToUserEntityById(task, userId);
    }

    @Override
    public ResponseEntity<TaskDTO> buildResponseEntity(TaskDTO taskDTO, HttpStatus httpStatus) {
        return new ResponseEntity<>(taskDTO, httpStatus);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
