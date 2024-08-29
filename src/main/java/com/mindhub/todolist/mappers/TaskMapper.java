package com.mindhub.todolist.mappers;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskCurrentApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.enums.TaskStatus;
import com.mindhub.todolist.models.Task;

import java.util.List;
import java.util.Set;

public class TaskMapper {

    // to DTO

    public static TaskDTO taskToTaskDTO(Task task) {
        return new TaskDTO(task);
    }

    public static Set<TaskDTO> tasksToTaskDTOs(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::taskToTaskDTO).collect(java.util.stream.Collectors.toSet());
    }

    public static Set<TaskDTO> tasksToTaskDTOs(Set<Task> tasks) {
        return tasks.stream().map(TaskMapper::taskToTaskDTO).collect(java.util.stream.Collectors.toSet());
    }

    // to Entity

    public static Task taskDTOToTask(TaskApplicationDTO taskApp) {
        return new Task(taskApp.title(), taskApp.description(), TaskStatus.valueOf(taskApp.taskStatus()));
    }

    public static Task taskDTOToTask(TaskCurrentApplicationDTO taskApp) {
        return new Task(taskApp.title(), taskApp.description(), TaskStatus.valueOf(taskApp.taskStatus()));
    }


}
