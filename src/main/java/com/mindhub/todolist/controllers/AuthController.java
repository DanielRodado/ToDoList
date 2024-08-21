package com.mindhub.todolist.controllers;

import com.mindhub.todolist.configurations.JwtUtils;
import com.mindhub.todolist.dto.LoginUser;
import com.mindhub.todolist.dto.UserEntityApplicationDTO;
import com.mindhub.todolist.dto.UserEntityDTO;
import com.mindhub.todolist.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
            description = "Authenticates the user with the provided credentials and generates a JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for authentication.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginUser.class),
                            examples = @ExampleObject(
                                            name = "Login Example",
                                            summary = "Standard login credentials",
                                            description = "Example data for user authentication with username and password.",
                                            value = "{ \"username\": \"john.doe\", \"password\": \"securepassword\" }"
                            )

                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT token generated successfully.",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    name = "JWT Token Example",
                                    summary = "JWT Token",
                                    description = "Example JWT token returned upon successful authentication.",
                                    value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\" }"
                            )
                    )
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

    @Operation(
            summary = "Create a new user.",
            description = "Creates a new user entity based on the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The user data to create a new user.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntityApplicationDTO.class),
                            examples = @ExampleObject(
                                            name = "User Registration Example",
                                            summary = "User registration details",
                                            description = "Example data for registering a new user.",
                                            value = "{ \"username\": \"Jane12\", \"email\": \"jane@example.com\", \"password\": \"securepassword\" }"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserEntityDTO.class),
                            examples = @ExampleObject(
                                    name = "User Created Example",
                                    summary = "Created User",
                                    description = "Example of the response when a new user is successfully created.",
                                    value = "{ \"id\": 1, \"username\": \"Jane12\", \"email\": \"jane.doe@example.com\" }"
                            )
                    )
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
