package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dto.TaskApplicationDTO;
import com.mindhub.todolist.dto.TaskAuthApplicationDTO;
import com.mindhub.todolist.dto.TaskDTO;
import com.mindhub.todolist.dto.TaskUpdateDTO;
import com.mindhub.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @Operation(summary = "Get all tasks.", description = "Fetches a set of all tasks represented as TaskDTO objects.")
    @ApiResponse(
            responseCode = "200",
            description = "Tasks retrieved successfully.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class)))
    @GetMapping
    public Set<TaskDTO> getAllTasksDTO() {
        return taskService.getAllTasksDTO();
    }

    @Operation(summary = "Retrieve task by ID", description = "Fetches the task details for the given task ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task successfully recovered.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found."
            )})
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskDTOById(
            @Parameter(description = "ID of the task to be recovered.", example = "1", required = true)
            @PathVariable("taskId") Long taskId) {
        return taskService.requestGetTaskDTOById(taskId);
    }

    @Operation(summary = "Create a new task", description = "Creates a new task based on the provided TaskApplicationDTO object.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Title task invalid."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Description task invalid."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Status task task invalid."
            )})
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @Parameter(description = "The task data to create a new task.", required = true)
            @RequestBody TaskApplicationDTO taskApp) {
        return taskService.requestCreateTask(taskApp);
    }

    @Operation(
            summary = "Update an existing task.",
            description = "Updates the task identified by the given task ID with the provided TaskUpdateDTO data.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Title task invalid."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Description task invalid."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Status task task invalid."
            )})
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @Parameter(description = "The ID of the task to update.", example = "1", required = true)
            @PathVariable("taskId") Long taskId,

            @Parameter(description = "The task data to update the existing task.", required = true)
            @RequestBody TaskUpdateDTO taskUpdate) {
        return taskService.requestUpdateTask(taskId, taskUpdate);
    }

    @Operation(summary = "Delete a task", description = "Deletes the task identified by the given task ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task deleted successfully."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found."
            )})
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
            @Parameter(description = "The ID of the task to delete.", example = "1", required = true)
            @PathVariable("taskId") Long taskId) {
        return taskService.requestDeleteTask(taskId);
    }

    // authenticated

    @Operation(summary = "Retrieve task by ID", description = "Fetches the task details for the given task ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task successfully recovered.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found."
            )})
    @GetMapping("/auth")
    public Set<TaskDTO> getTaskDTOAuth(Authentication auth) {
        return taskService.getTaskDTOByUserAuth(auth.getName());
    }

    @PostMapping("/auth")
    public ResponseEntity<TaskDTO> createNewTaskAuth(@RequestBody TaskAuthApplicationDTO taskAuthApp,
                                                     Authentication auth) {
        return taskService.requestCreateNewTaskAuth(taskAuthApp, auth.getName());
    }

    @PutMapping("/auth/{taskId}")
    public ResponseEntity<TaskDTO> updateTaskAuthById(@RequestBody TaskUpdateDTO taskUpdate,
                                                   @PathVariable("taskId") Long taskId,
                                                  Authentication auth) {
        return taskService.requestUpdateTaskAuth(taskUpdate, taskId, auth.getName());
    }

    @DeleteMapping("/auth/{taskId}")
    public ResponseEntity<?> deleteTaskAuthById(@PathVariable("taskId") Long taskId, Authentication auth) {
        return taskService.requestDeleteTaskAuth(taskId, auth.getName());
    }

}
