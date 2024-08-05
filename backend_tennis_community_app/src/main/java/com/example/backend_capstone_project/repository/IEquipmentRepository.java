package com.example.backend_capstone_project.repository;

import com.example.backend_capstone_project.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEquipmentRepository extends JpaRepository<Equipment, Integer> {
}
