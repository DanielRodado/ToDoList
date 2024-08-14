package com.mindhub.todolist.dto;

import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.models.Task;

public class TaskDTO {

    private final Long id;

    private final String title, description;

    private final TaskStatus taskStatus;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.taskStatus = task.getTaskStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}
