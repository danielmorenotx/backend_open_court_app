package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.DTO.LoginDTO;
import com.example.backend_capstone_project.DTO.UserDTO;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.UserService;
import com.example.backend_capstone_project.util.UserConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter userConverter;

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

    private UserDTO testUserDTO = UserDTO.builder()
            .id(1)
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .isPlayer(true)
            .isCoach(false)
            .build();

    @Test
    public void testAddUser_Success() throws Exception {
        when(userService.addUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").doesNotExist()); // checks that the password doesn't exist in what's returned
    }

    @Test
    public void testAuthUserLogin_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("john@example.com", "password123");

        when(userService.authUserLogin(any(LoginDTO.class))).thenReturn(testUser);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }
}
