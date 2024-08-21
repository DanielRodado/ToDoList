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
@Tag(name = "Task Management", description = "Operations related to task management.")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // authenticated

    @Operation(summary = "Get tasks for authenticated user.",
                description = "Retrieves the tasks associated with the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the tasks for the authenticated user.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/users/current")
    public Set<TaskDTO> getTaskDTOCurrentUser(@Parameter(hidden = true) Authentication auth) {
        return taskService.getTaskDTOByUserAuth(auth.getName());
    }

    @Operation(summary = "Create a new task for authenticated user.",
                description = "Creates a new task associated with the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied or invalid input data.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/users/current")
    public ResponseEntity<TaskDTO> createNewTaskAuth(
            @Parameter(description = "Task data to create a new task for the authenticated user.", required = true)
            @RequestBody TaskAuthApplicationDTO taskAuthApp,

            @Parameter(hidden = true)
            Authentication auth) {
        return taskService.requestCreateNewTaskAuth(taskAuthApp, auth.getName());
    }

    @Operation(summary = "Update a task by ID for authenticated user.",
                description = "Updates an existing task identified by its ID, associated with the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied or invalid input data.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found.",
                    content = @Content(schema = @Schema(hidden = true))
            ),
    })
    @PutMapping("/users/current/{taskId}")
    public ResponseEntity<TaskDTO> updateTaskAuthById(
            @Parameter(description = "Task data to update the task.", required = true)
            @RequestBody TaskUpdateDTO taskUpdate,

            @Parameter(description = "ID of the task to update.", required = true)
            @PathVariable("taskId") Long taskId,

            @Parameter(hidden = true)
            Authentication auth) {
        return taskService.requestUpdateTaskAuth(taskUpdate, taskId, auth.getName());
    }

    @Operation(summary = "Delete a task by ID for authenticated user.",
                description = "Deletes an existing task identified by its ID, associated with the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task deleted successfully."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found."
            )
    })
    @DeleteMapping("/users/current/{taskId}")
    public ResponseEntity<?> deleteTaskAuthById(
            @Parameter(description = "ID of the task to delete.")
            @PathVariable("taskId") Long taskId,

            @Parameter(hidden = true)
            Authentication auth) {
        return taskService.requestDeleteTaskAuth(taskId, auth.getName());
    }

}
