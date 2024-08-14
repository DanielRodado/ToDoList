package com.mindhub.todolist.services;

import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.models.Task;

public interface TaskService {

    Task getTaskById(Long id);

    TaskDTO getTaskDTOById(Long id);

    void saveTask(Task task);

}
