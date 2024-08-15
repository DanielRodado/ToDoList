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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.mindhub.todolist.utils.ResponseHelper.createResponse;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Methods

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Task not found for the id provided"));
    }

    @Override
    public TaskDTO getTaskDTOById(Long id) {
        return new TaskDTO(getTaskById(id));
    }

    @Override
    public ResponseEntity<TaskDTO> requestGetTaskDTOById(Long id) {
        return ResponseEntity.ok(getTaskDTOById(id));
    }

    // Create new Task
    @Override
    public ResponseEntity<String> requestCreateTask(TaskApplicationDTO taskApp) {
        validateTaskApp(taskApp);
        Task task = createTask(taskApp);
        saveTask(task);
        return createResponse("Task created", HttpStatus.CREATED);
    }

    @Override
    public void validateTaskApp(TaskApplicationDTO taskApp) {
        validateTaskTitle(taskApp.title());
        validateTaskDescription(taskApp.description());
        validateTaskStatus(taskApp.taskStatus());
    }

    @Override
    public void validateTaskTitle(String title) {
        if (title.isBlank()) {
            throw new InvalidFieldInputTaskException("The title cannot be empty");
        }
    }

    @Override
    public void validateTaskDescription(String description) {
        if (description.isBlank()) {
            throw new InvalidFieldInputTaskException("The description cannot be empty");
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
    public Task createTask(TaskApplicationDTO taskApp) {
        return new Task(taskApp.title(), taskApp.description(), TaskStatus.valueOf(taskApp.taskStatus()));
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
