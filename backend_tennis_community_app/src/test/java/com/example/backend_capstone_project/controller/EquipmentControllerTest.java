package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.Equipment;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipmentController.class)
@AutoConfigureMockMvc
public class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentService equipmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser = User.builder()
            .id(1)
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .password("password123")
            .isPlayer(true)
            .isCoach(false)
            .build();

    private Equipment testEquipment = Equipment.builder()
            .id(1)
            .name("Tennis Racquet")
            .owners(List.of(testUser))
            .build();

    private List<Equipment> testEquipmentList = List.of(testEquipment);

    @Test
    public void testAddEquipment_Success() throws Exception {
        when(equipmentService.addEquipment(any(Equipment.class))).thenReturn(testEquipment);

        mockMvc.perform(post("/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEquipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEquipment.getId()))
                .andExpect(jsonPath("$.name").value(testEquipment.getName()))
                .andExpect(jsonPath("$.owners[0].id").value(testUser.getId()));
    }

    @Test
    public void testGetAllEquipment_Success() throws Exception {
        when(equipmentService.getAllEquipment()).thenReturn(testEquipmentList);

        mockMvc.perform(get("/equipment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testEquipment.getId()))
                .andExpect(jsonPath("$[0].name").value(testEquipment.getName()))
                .andExpect(jsonPath("$[0].owners[0].id").value(testUser.getId()));
    }

    @Test
    public void testGetEquipmentById_Success() throws Exception {
        when(equipmentService.getEquipmentById(anyInt())).thenReturn(testEquipment);

        mockMvc.perform(get("/equipment/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEquipment.getId()))
                .andExpect(jsonPath("$.name").value(testEquipment.getName()))
                .andExpect(jsonPath("$.owners[0].id").value(testUser.getId()));
    }

    @Test
    public void testUpdateEquipment_Success() throws Exception {
        when(equipmentService.updateEquipment(anyInt(), any(Equipment.class))).thenReturn(testEquipment);

        mockMvc.perform(put("/equipment/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEquipment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEquipment.getId()))
                .andExpect(jsonPath("$.name").value(testEquipment.getName()))
                .andExpect(jsonPath("$.owners[0].id").value(testUser.getId()));
    }

    @Test
    public void testDeleteEquipment_Success() throws Exception {
        doNothing().when(equipmentService).deleteEquipment(anyInt());

        mockMvc.perform(delete("/equipment/{id}", 1))
                .andExpect(status().isNoContent());

        verify(equipmentService, times(1)).deleteEquipment(1);
    }
}

