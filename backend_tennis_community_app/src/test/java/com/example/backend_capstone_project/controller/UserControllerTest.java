package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser = User.builder()
            .id(1)
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("password123")
            .isPlayer(true)
            .isCoach(false)
            .build();

    private List<User> testUsers = List.of(testUser);

    @Test
    public void testGetAllUsers_Success() throws Exception {
        when(userService.getAllUsers()).thenReturn(testUsers);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);

        mockMvc.perform(get("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist()); // Ensure password is not exposed
    }

    @Test
    public void testGetAllCoaches_Success() throws Exception {
        when(userService.getAllCoaches()).thenReturn(testUsers);

        mockMvc.perform(get("/users/coaches")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        when(userService.updateUser(anyInt(), any(User.class))).thenReturn(testUser);

        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist()); // Ensure password is not exposed
    }

    @Test
    public void testDeleteUser_Success() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1);
    }
}
