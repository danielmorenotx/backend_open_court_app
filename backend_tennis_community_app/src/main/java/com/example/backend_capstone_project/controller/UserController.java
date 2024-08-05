package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.DTO.UserDTO;
import com.example.backend_capstone_project.util.UserConverter;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    // ======= GET ALL USERS ========
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                // method reference to take every element and runs it through the convertToDTO method
                .map(UserConverter::convertToDTO)
                // converts to a new List
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // ======= GET USER BY ID ========
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) throws Exception {
        User user = userService.getUserById(id);
        UserDTO userDTO = UserConverter.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    // ======= GET USER'S NAME BY ID ========
    @GetMapping("/{id}/name")
    public String getUserNameById(@PathVariable Integer id) throws Exception {
        return userService.getUserNameById(id);
    }

    // ======= GET ALL PLAYERS ========
    @GetMapping("/players")
    public ResponseEntity<List<UserDTO>> getAllPlayers() {
        List<User> players = userService.getAllPlayers();
        List<UserDTO> playerDTOs = players.stream()
                .map(UserConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerDTOs);
    }

    // ======= GET ALL COACHES ========
    @GetMapping("/coaches")
    public ResponseEntity<List<UserDTO>> getAllCoaches() {
        List<User> coaches = userService.getAllCoaches();
        List<UserDTO> coachDTOs = coaches.stream()
                .map(UserConverter::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(coachDTOs);
    }

    // ======= UPDATE USER ========
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) throws Exception {
        User updated = userService.updateUser(id, updatedUser);
        UserDTO userDTO = UserConverter.convertToDTO(updated);
        return ResponseEntity.ok(userDTO);
    }

    // ======= DELETE USER ========
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    // ======== GET USER BY ZIP CODE ==========
    @GetMapping("/zip/{zipCode}")
    public ResponseEntity<List<User>> getUsersByZipCode(@PathVariable String zipCode) {
        List<User> users = userService.findUsersByZipCode(zipCode);
        return ResponseEntity.ok(users);
    }

}