package com.example.backend_capstone_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String status;
    private String location;
    private Date dateScheduled;
    private boolean playerNeedsEquipment;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private User player;

    // Reference to the coach
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;
}
