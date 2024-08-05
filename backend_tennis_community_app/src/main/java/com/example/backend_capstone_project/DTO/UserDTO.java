package com.example.backend_capstone_project.DTO;

import com.example.backend_capstone_project.model.Address;
import com.example.backend_capstone_project.model.Equipment;
import com.example.backend_capstone_project.model.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Date signupDate;
    private Address address;
    private Boolean isPlayer;
    private Boolean needToBorrowRacquet;
    private String playerExperienceDescription;
    private String tennisLevel;
    private List<Lesson> playerLessons;
    private Boolean isCoach;
    private String coachExperienceDescription;
    private Boolean canLendRacquet;
    private Boolean canSupplyBalls;
    private Double coachRating;
    private List<Lesson> coachLessons;
    private List<Equipment> ownedEquipment;
}