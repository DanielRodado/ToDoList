package com.mindhub.todolist.controllerTest;

import com.mindhub.todolist.controllers.TaskController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostTask() throws Exception {
        String requestBody = "{ \"title\": \"john.doe\", \"description\": \"test\", \"status\": \"PENDING\", \"user\": { \"id\": 1 } }";
        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
}
