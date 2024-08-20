package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.exceptions.userExceptions.NotFoundUserEntityException;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Operations related to user management.")
public class UserEntityController {

    @Autowired
    private UserEntityService userEntityService;

    @Operation(
            summary = "Get all users",
            description = "Retrieves a set of all user entities in the system."
    )
     @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the list of users.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class)))
    @GetMapping
    public Set<UserEntityDTO> getAllUsersDTO() {
        return userEntityService.getAllUserEntityDTO();
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user entity by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{userEntityId}")
    public UserEntityDTO getUserDTOById(@PathVariable("userEntityId") Long userEntityId) {
        return userEntityService.findUserEntityDTOById(userEntityId);
    }

    @Operation(summary = "Create a new user.", description = "Creates a new user entity based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    public ResponseEntity<UserEntityDTO> createNewUser(
            @Parameter(description = "The user data to create a new user.", required = true)
            @RequestBody UserEntityApplicationDTO userApp) {
        return userEntityService.requestCreateUserEntity(userApp);
    }

    // Auth User

    @Operation(summary = "Get authenticated user information.",
                description = "Retrieves the user entity associated with the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the authenticated user.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/auth")
    public UserEntityDTO getUserAuth(@Parameter(hidden = true) Authentication auth) {
        return userEntityService.findUserEntityDTOByUsername(auth.getName());
    }

}
