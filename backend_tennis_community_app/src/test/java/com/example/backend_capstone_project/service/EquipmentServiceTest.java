package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Equipment;
import com.example.backend_capstone_project.repository.IEquipmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EquipmentServiceTest {

    @Autowired
    private EquipmentService equipmentService;

    @MockBean
    private IEquipmentRepository equipmentRepository;

    private Equipment testEquipment = Equipment.builder()
            .id(1)
            .name("Tennis Racquet")
            .build();

    private List<Equipment> testEquipments = List.of(testEquipment);

    @Test
    public void testAddEquipment_Success() {
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(testEquipment);

        Equipment savedEquipment = equipmentService.addEquipment(testEquipment);

        assertEquals(testEquipment, savedEquipment);
        verify(equipmentRepository, times(1)).save(testEquipment);
    }

    @Test
    public void testGetAllEquipment_Success() {
        when(equipmentRepository.findAll()).thenReturn(testEquipments);

        List<Equipment> equipments = equipmentService.getAllEquipment();

        assertEquals(testEquipments, equipments);
        verify(equipmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetEquipmentById_Success() {
        when(equipmentRepository.findById(anyInt())).thenReturn(Optional.of(testEquipment));

        Equipment equipment = equipmentService.getEquipmentById(1);

        assertEquals(testEquipment, equipment);
        verify(equipmentRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateEquipment_Success() {
        when(equipmentRepository.existsById(anyInt())).thenReturn(true);
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(testEquipment);

        Equipment updatedEquipment = Equipment.builder()
                .name("New Tennis Racquet")
                .build();

        Equipment savedEquipment = equipmentService.updateEquipment(1, updatedEquipment);

        assertEquals(testEquipment, savedEquipment);
        verify(equipmentRepository, times(1)).existsById(1);
        verify(equipmentRepository, times(1)).save(updatedEquipment);
    }

    @Test
    public void testDeleteEquipment_Success() {
        when(equipmentRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(equipmentRepository).deleteById(anyInt());

        equipmentService.deleteEquipment(1);

        verify(equipmentRepository, times(1)).existsById(1);
        verify(equipmentRepository, times(1)).deleteById(1);
    }
}

