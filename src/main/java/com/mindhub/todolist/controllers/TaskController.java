package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskDTOById(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskDTOById(taskId));
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskApplicationDTO taskApp) {
        return taskService.requestCreateTask(taskApp);
    }

}
