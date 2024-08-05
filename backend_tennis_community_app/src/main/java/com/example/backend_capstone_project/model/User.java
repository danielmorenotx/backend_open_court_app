package com.example.backend_capstone_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "app_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Your password must be at least 8 characters long")
    private String password;

    private Date dateOfBirth;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date signupDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = true)
    @ToString.Exclude // Exclude address from toString to avoid recursion
    private Address address;

    // Player-specific fields
    private Boolean isPlayer = false;
    private Boolean needToBorrowRacquet;
    private String playerExperienceDescription;
    private String tennisLevel;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude // Exclude playerLessons from toString to avoid recursion
    private List<Lesson> playerLessons;

    // Coach-specific fields
    private Boolean isCoach = false;
    private String coachExperienceDescription;
    private Boolean canLendRacquet;
    private Boolean canSupplyBalls;
    private Double coachRating;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude // Exclude coachLessons from toString to avoid recursion
    private List<Lesson> coachLessons;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "coach_equipment",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private List<Equipment> ownedEquipment;

}
