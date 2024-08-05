package com.example.backend_capstone_project.util;

import com.example.backend_capstone_project.DTO.UserDTO;
import com.example.backend_capstone_project.model.User;

public class UserConverter {
    public static UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getSignupDate(),
                user.getAddress(),
                user.getIsPlayer(),
                user.getNeedToBorrowRacquet(),
                user.getPlayerExperienceDescription(),
                user.getTennisLevel(),
                user.getPlayerLessons(),
                user.getIsCoach(),
                user.getCoachExperienceDescription(),
                user.getCanLendRacquet(),
                user.getCanSupplyBalls(),
                user.getCoachRating(),
                user.getCoachLessons(),
                user.getOwnedEquipment()
        );
    }
}
