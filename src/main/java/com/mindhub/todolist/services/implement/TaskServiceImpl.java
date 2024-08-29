package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskCurrentApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.exceptions.taskExceptions.InvalidFieldInputTaskException;
import com.mindhub.todolist.exceptions.taskExceptions.InvalidTaskStatusException;
import com.mindhub.todolist.exceptions.taskExceptions.NotFoundTaskException;
import com.mindhub.todolist.exceptions.taskExceptions.TaskNotBelongToUserException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.mindhub.todolist.mappers.TaskMapper.*;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserEntityService userEntityService;

    // Methods

    @Override
    public boolean existsTaskById(Long id) {
        return taskRepository.existsById(id);
    }

    @Override
    public boolean existsTaskByIdAndUserEntity(Long id, String username) {
        UserEntity userEntity = userEntityService.findUserEntityByUsername(username);
        return taskRepository.existsByIdAndUserEntity(id, userEntity);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Task not found."));
    }

    @Override
    public TaskDTO getTaskDTOById(Long id) {
        return taskToTaskDTO(getTaskById(id));
    }

    @Override
    public TaskDTO transformToTaskDTO(Task task) {
        return taskToTaskDTO(task);
    }

    @Override
    public Set<Task> getTaskByCurrentUser(String username) {
        return taskRepository.findByUserEntity(userEntityService.findUserEntityByUsername(username));
    }

    @Override
    public Set<TaskDTO> getTaskDTOByCurrentUser(String username) {
        return tasksToTaskDTOs(getTaskByCurrentUser(username));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Set<TaskDTO> getAllTasksDTO() {
        return tasksToTaskDTOs(getAllTasks());
    }

    @Override
    public ResponseEntity<TaskDTO> requestGetTaskDTOById(Long id) {
        return ResponseEntity.ok(getTaskDTOById(id));
    }

    // Create new Task
    @Override
    public ResponseEntity<TaskDTO> requestCreateTask(TaskApplicationDTO taskApp) {
        validateTaskApplication(taskApp);
        Task task = taskDTOToTask(taskApp);
        associateTaskWithUser(task, taskApp.user().id());
        saveTask(task);
        return buildResponseEntity(taskToTaskDTO(task), HttpStatus.CREATED);
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

    // Update Task
    @Override
    public ResponseEntity<TaskDTO> requestUpdateTask(Long id, TaskCurrentApplicationDTO taskApp) {
        validateTaskUpdate(taskApp);
        Task task = getTaskById(id);
        updateTask(task, taskApp);
        saveTask(task);
        return buildResponseEntity(taskToTaskDTO(task), HttpStatus.OK);
    }

    @Override
    public void validateTaskUpdate(TaskCurrentApplicationDTO taskApp) {
        validateTaskTitle(taskApp.title());
        validateTaskDescription(taskApp.description());
        validateTaskStatus(taskApp.taskStatus());
    }

    @Override
    public void updateTask(Task task, TaskCurrentApplicationDTO taskApp) {
        task.setTitle(taskApp.title());
        task.setDescription(taskApp.description());
        task.setTaskStatus(TaskStatus.valueOf(taskApp.taskStatus()));
    }

    @Override
    public ResponseEntity<?> requestDeleteTask(Long id) {
        validateExistsTaskById(id);
        deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void validateExistsTaskById(Long id) {
        if (!existsTaskById(id)) {
            throw new NotFoundTaskException("Not found task.");
        }
    }

    @Override
    public ResponseEntity<TaskDTO> requestCreateNewTaskCurrentUser(TaskCurrentApplicationDTO taskCurrentApp, String username) {
        validateTaskCurrentApplication(taskCurrentApp);
        Task task = taskDTOToTask(taskCurrentApp);
        associateTaskWithUserByUsername(task, username);
        saveTask(task);
        return buildResponseEntity(taskToTaskDTO(task), HttpStatus.CREATED);
    }

    @Override
    public void validateTaskCurrentApplication(TaskCurrentApplicationDTO taskCurrentApp) {
        validateTaskTitle(taskCurrentApp.title());
        validateTaskDescription(taskCurrentApp.description());
        validateTaskStatus(taskCurrentApp.taskStatus());
    }

    @Override
    public Task buildTaskAuthFromDTO(TaskCurrentApplicationDTO taskAuthApp) {
        return new Task(taskAuthApp.title(), taskAuthApp.description(), TaskStatus.valueOf(taskAuthApp.taskStatus()));
    }

    @Override
    public void associateTaskWithUserByUsername(Task task, String username) {
        userEntityService.addTaskToUserEntityByUsername(task, username);
    }

    @Override
    public ResponseEntity<TaskDTO> requestUpdateTaskCurrentUser(TaskCurrentApplicationDTO taskCurrentApp, Long id, String username) {
        validateTaskBelongsToUser(id, username);
        return requestUpdateTask(id, taskCurrentApp);
    }

    @Override
    public void validateTaskBelongsToUser(Long id, String username) {
        if (!existsTaskByIdAndUserEntity(id, username)) {
            throw new TaskNotBelongToUserException("The task indicated does not belong to you.");
        }
    }

    @Override
    public ResponseEntity<?> requestDeleteTaskCurrentUser(Long id, String username) {
        validateExistsTaskById(id);
        validateTaskBelongsToUser(id, username);
        deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
