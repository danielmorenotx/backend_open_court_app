package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Equipment;
import com.example.backend_capstone_project.repository.IEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EquipmentService {

    @Autowired
    IEquipmentRepository equipmentRepository;

    // ======= ADD =======
    public Equipment addEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    // ======= GET ALL =======
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    // ======= GET BY ID =======
    public Equipment getEquipmentById(Integer id) {
        return equipmentRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    // ======= UPDATE =======
    public Equipment updateEquipment(Integer id, Equipment updatedEquipment) {
        if (!equipmentRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        updatedEquipment.setId(id); // Ensure the ID of the updated equipment matches the ID in the path
        return equipmentRepository.save(updatedEquipment);
    }

    // ======= DELETE =======
    public void deleteEquipment(Integer id) {
        if (!equipmentRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        equipmentRepository.deleteById(id);
    }
}

