package com.example.backend_capstone_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    @ToString.Exclude // Exclude address from toString to avoid recursion
    private User user;
}
