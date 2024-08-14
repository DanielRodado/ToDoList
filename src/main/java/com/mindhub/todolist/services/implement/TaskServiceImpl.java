package com.mindhub.todolist.services.implement;

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
    public void saveTask(Task task) {
        taskRepository.save(task);
    }
}
