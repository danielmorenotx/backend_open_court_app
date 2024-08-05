package com.example.backend_capstone_project.repository;

import com.example.backend_capstone_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    // find users in a certain zip code
    @Query("SELECT u FROM User u JOIN u.address a WHERE a.zipCode = :zipCode")
    List<User> findUsersByZipCode(@Param("zipCode") String zipCode);
}
