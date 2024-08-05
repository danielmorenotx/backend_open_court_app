package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.DTO.LoginDTO;
import com.example.backend_capstone_project.exception.InvalidPasswordException;
import com.example.backend_capstone_project.exception.UserNotFoundException;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    // ======= USER SIGNUP (ADD) ========
    public User addUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("This email address is already in use.");
        }
        return userRepository.save(user);
    }

    // ======= USER LOGIN ========
    public User authUserLogin(LoginDTO loginDTO) throws UserNotFoundException, InvalidPasswordException {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return user;
    }

    // ======= GET ALL ========
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ======= GET USER BY ID ========
    public User getUserById(Integer id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with ID " + id));
    }

    // ======= GET USER'S NAME BY ID ========
    public String getUserNameById(Integer id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with ID " + id));

        String userFullName = user.getFirstName() + " " + user.getLastName();

        return userFullName;
    }

    // ======= GET ALL PLAYERS ========
    public List<User> getAllPlayers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(User::getIsPlayer) // checks if the user is marked as a player
                .toList();
    }

    // ======= GET ALL COACHES ========
    public List<User> getAllCoaches() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(User::getIsCoach) // checks if the user is marked as a coach
                .toList();
    }

    // ======= UPDATE USER ========
    public User updateUser(Integer id, User updatedUser) throws Exception {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with ID " + id));

        // Update the user fields one by one
        // only presents value if it is not null. If present, it updates the field
        Optional.ofNullable(updatedUser.getFirstName()).ifPresent(userToUpdate::setFirstName);
        Optional.ofNullable(updatedUser.getLastName()).ifPresent(userToUpdate::setLastName);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(userToUpdate::setEmail);
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(userToUpdate::setPassword);
        Optional.ofNullable(updatedUser.getDateOfBirth()).ifPresent(userToUpdate::setDateOfBirth);
        Optional.ofNullable(updatedUser.getAddress()).ifPresent(userToUpdate::setAddress);
        Optional.ofNullable(updatedUser.getIsPlayer()).ifPresent(userToUpdate::setIsPlayer);
        Optional.ofNullable(updatedUser.getNeedToBorrowRacquet()).ifPresent(userToUpdate::setNeedToBorrowRacquet);
        Optional.ofNullable(updatedUser.getPlayerExperienceDescription()).ifPresent(userToUpdate::setPlayerExperienceDescription);
        Optional.ofNullable(updatedUser.getTennisLevel()).ifPresent(userToUpdate::setTennisLevel);
        Optional.ofNullable(updatedUser.getPlayerLessons()).ifPresent(userToUpdate::setPlayerLessons);
        Optional.ofNullable(updatedUser.getIsCoach()).ifPresent(userToUpdate::setIsCoach);
        Optional.ofNullable(updatedUser.getCoachExperienceDescription()).ifPresent(userToUpdate::setCoachExperienceDescription);
        Optional.ofNullable(updatedUser.getCanLendRacquet()).ifPresent(userToUpdate::setCanLendRacquet);
        Optional.ofNullable(updatedUser.getCanSupplyBalls()).ifPresent(userToUpdate::setCanSupplyBalls);
        Optional.ofNullable(updatedUser.getCoachRating()).ifPresent(userToUpdate::setCoachRating);
        Optional.ofNullable(updatedUser.getCoachLessons()).ifPresent(userToUpdate::setCoachLessons);
        Optional.ofNullable(updatedUser.getOwnedEquipment()).ifPresent(userToUpdate::setOwnedEquipment);

        return userRepository.save(userToUpdate);
    }


    // ======= DELETE USER ========
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    // ======== GET USER BY ZIP CODE ==========
    public List<User> findUsersByZipCode(String zipCode) {
        return userRepository.findUsersByZipCode(zipCode);
    }
}