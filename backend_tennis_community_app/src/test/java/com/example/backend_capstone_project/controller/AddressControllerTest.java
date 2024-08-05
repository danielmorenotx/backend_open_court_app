package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.Address;
import com.example.backend_capstone_project.service.AddressService;
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

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private Address testAddress = Address.builder()
            .id(1)
            .streetAddress("123 Main St")
            .city("Springfield")
            .state("IL")
            .zipCode("62701")
            .build();

    private List<Address> testAddresses = List.of(testAddress);

    @Test
    public void testAddAddress_Success() throws Exception {
        when(addressService.addAddress(any(Address.class))).thenReturn(testAddress);

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAddress.getId()))
                .andExpect(jsonPath("$.streetAddress").value(testAddress.getStreetAddress()))
                .andExpect(jsonPath("$.city").value(testAddress.getCity()))
                .andExpect(jsonPath("$.state").value(testAddress.getState()))
                .andExpect(jsonPath("$.zipCode").value(testAddress.getZipCode()));
    }

    @Test
    public void testGetAllAddresses_Success() throws Exception {
        when(addressService.getAllAddresses()).thenReturn(testAddresses);

        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAddress.getId()))
                .andExpect(jsonPath("$[0].streetAddress").value(testAddress.getStreetAddress()))
                .andExpect(jsonPath("$[0].city").value(testAddress.getCity()))
                .andExpect(jsonPath("$[0].state").value(testAddress.getState()))
                .andExpect(jsonPath("$[0].zipCode").value(testAddress.getZipCode()));
    }

    @Test
    public void testGetAddressById_Success() throws Exception {
        when(addressService.getAddressById(anyInt())).thenReturn(testAddress);

        mockMvc.perform(get("/addresses/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAddress.getId()))
                .andExpect(jsonPath("$.streetAddress").value(testAddress.getStreetAddress()))
                .andExpect(jsonPath("$.city").value(testAddress.getCity()))
                .andExpect(jsonPath("$.state").value(testAddress.getState()))
                .andExpect(jsonPath("$.zipCode").value(testAddress.getZipCode()));
    }

    @Test
    public void testUpdateAddress_Success() throws Exception {
        when(addressService.updateAddress(anyInt(), any(Address.class))).thenReturn(testAddress);

        mockMvc.perform(put("/addresses/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAddress.getId()))
                .andExpect(jsonPath("$.streetAddress").value(testAddress.getStreetAddress()))
                .andExpect(jsonPath("$.city").value(testAddress.getCity()))
                .andExpect(jsonPath("$.state").value(testAddress.getState()))
                .andExpect(jsonPath("$.zipCode").value(testAddress.getZipCode()));
    }

    @Test
    public void testDeleteAddress_Success() throws Exception {
        doNothing().when(addressService).deleteAddress(anyInt());

        mockMvc.perform(delete("/addresses/{id}", 1))
                .andExpect(status().isNoContent());

        verify(addressService, times(1)).deleteAddress(1);
    }
}
