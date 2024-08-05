package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.DTO.ResponseDTO;
import com.example.backend_capstone_project.exception.InvalidPasswordException;
import com.example.backend_capstone_project.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.example.backend_capstone_project.DTO.LoginDTO;
import com.example.backend_capstone_project.DTO.UserDTO;
import com.example.backend_capstone_project.util.UserConverter;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    UserService userService;

    // ======= USER SIGNUP ========
    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult result) { // result is passed by the validator
        if (result.hasErrors()) { // checks if result violates any of the validators in the model
            Map<String, String> errorMap = new HashMap<>(); // creates HashMap where both keys and values are strings
            result.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));  // "puts" the field and message as the key and value
            String combinedErrors = String.join(", ", errorMap.values());
            ResponseDTO response = new ResponseDTO(combinedErrors, "Validation failed", true);
            return ResponseEntity.badRequest().body(response); // sends the HashMap along with the error
        } else {
            try {
                User savedUser = userService.addUser(user);
                UserDTO userDTO = UserConverter.convertToDTO(savedUser);
                return ResponseEntity.ok(userDTO);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    // ======= USER LOGIN ========
    @PostMapping("/login")
    public ResponseEntity<?> authUserLogin(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            result.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
            String combinedErrors = String.join(", ", errorMap.values());
            ResponseDTO response = new ResponseDTO(combinedErrors, "Validation failed", true);
            return ResponseEntity.badRequest().body(response);
        } else {
            try {
                User user = userService.authUserLogin(loginDTO);
                UserDTO userDTO = UserConverter.convertToDTO(user);
                ResponseDTO response = new ResponseDTO("Login successful!", null, false);
                return ResponseEntity.ok(userDTO);
            } catch (UserNotFoundException e) {
                ResponseDTO response = new ResponseDTO(e.getMessage(), "User not found", true);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } catch (InvalidPasswordException e) {
                ResponseDTO response = new ResponseDTO(e.getMessage(), "Invalid password", true);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }
    }
}