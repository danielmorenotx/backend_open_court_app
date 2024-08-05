package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.DTO.LoginDTO;
import com.example.backend_capstone_project.DTO.UserDTO;
import com.example.backend_capstone_project.exception.InvalidPasswordException;
import com.example.backend_capstone_project.exception.UserNotFoundException;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private IUserRepository userRepository;

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

    private LoginDTO loginDTO = LoginDTO.builder()
            .email("john@example.com")
            .password("password123")
            .build();


    @Test
    public void testAddUser_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty()); // should not find anything
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.addUser(testUser);

        assertEquals(testUser, savedUser);
    }

    @Test
    public void testAddUser_Fail_EmailExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(testUser));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(testUser);
        });
    }

    @Test
    public void testAuthUserLogin_Success() throws UserNotFoundException, InvalidPasswordException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(testUser));

        User authenticatedUser = userService.authUserLogin(loginDTO);

        assertEquals(testUser, authenticatedUser);
    }

    @Test
    public void testAuthUserLogin_Fail_InvalidPassword() {
        User userWithWrongPassword = User.builder().password("wrongpassword").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(userWithWrongPassword));

        assertThrows(InvalidPasswordException.class, () -> {
            userService.authUserLogin(loginDTO);
        });
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertEquals(users, retrievedUsers);
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(testUser));

        User retrievedUser = userService.getUserById(1);

        assertEquals(testUser, retrievedUser);
    }

    @Test
    public void testGetUserById_Fail_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            userService.getUserById(1);
        });
    }

    @Test
    public void testGetUserNameById_Success() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(testUser));

        String fullName = userService.getUserNameById(1);

        assertEquals("John Doe", fullName);
    }

    @Test
    public void testGetUserNameById_Fail_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            userService.getUserNameById(1);
        });
    }

    @Test
    public void testGetAllPlayers() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> players = userService.getAllPlayers();

        assertEquals(users, players);
    }

    @Test
    public void testGetAllCoaches() {
        User coachUser = User.builder().isPlayer(false).isCoach(true).build();
        List<User> users = List.of(coachUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> coaches = userService.getAllCoaches();

        assertEquals(users, coaches);
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        User updatedUser = User.builder().firstName("Jane").build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User resultUser = userService.updateUser(1, updatedUser);

        assertEquals("Jane", resultUser.getFirstName());
    }

    @Test
    public void testUpdateUser_Fail_UserNotFound() {
        User updatedUser = User.builder().firstName("Jane").build();
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            userService.updateUser(1, updatedUser);
        });
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteById(anyInt());

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
