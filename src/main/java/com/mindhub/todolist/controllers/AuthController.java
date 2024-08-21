package com.mindhub.todolist.controllers;

import com.mindhub.todolist.configurations.JwtUtils;
import com.mindhub.todolist.dto.LoginUser;
import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication.")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserEntityService userEntityService;

    @Operation(
            summary = "Authenticate user and generate JWT.",
            description = "Authenticates the user with the provided credentials and generates a JWT token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT token generated successfully.",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid credentials.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
            @Parameter(description = "User credentials for authentication.", required = true)
            @RequestBody LoginUser loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication.getName());
        return ResponseEntity.ok(jwt);
    }

    @Operation(summary = "Create a new user.", description = "Creates a new user entity based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntityDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid input data.",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserEntityDTO> createNewUser(
            @Parameter(description = "The user data to create a new user.", required = true)
            @RequestBody UserEntityApplicationDTO userApp) {
        return userEntityService.requestCreateUserEntity(userApp);
    }
}
