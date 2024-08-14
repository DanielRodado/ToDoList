package com.mindhub.todolist.services.implement;

import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.exceptions.NotFoundTaskException;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Task not found for the id provided"));
    }

    @Override
    public TaskDTO getTaskDTOById(Long id) {
        return new TaskDTO(getTaskById(id));
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
