package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Address;
import com.example.backend_capstone_project.repository.IAddressRepository;
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
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private IAddressRepository addressRepository;

    private Address testAddress = Address.builder()
            .id(1)
            .streetAddress("123 Main St")
            .city("Springfield")
            .state("IL")
            .zipCode("62701")
            .build();

    private List<Address> testAddresses = List.of(testAddress);

    @Test
    public void testAddAddress_Success() {
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);

        Address savedAddress = addressService.addAddress(testAddress);

        assertEquals(testAddress, savedAddress);
        verify(addressRepository, times(1)).save(testAddress);
    }

    @Test
    public void testGetAllAddresses_Success() {
        when(addressRepository.findAll()).thenReturn(testAddresses);

        List<Address> addresses = addressService.getAllAddresses();

        assertEquals(testAddresses, addresses);
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    public void testGetAddressById_Success() {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(testAddress));

        Address address = addressService.getAddressById(1);

        assertEquals(testAddress, address);
        verify(addressRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateAddress_Success() {
        when(addressRepository.existsById(anyInt())).thenReturn(true);
        when(addressRepository.save(any(Address.class))).thenReturn(testAddress);

        Address updatedAddress = Address.builder()
                .streetAddress("456 Elm St")
                .city("Springfield")
                .state("IL")
                .zipCode("62702")
                .build();

        Address savedAddress = addressService.updateAddress(1, updatedAddress);

        assertEquals(testAddress, savedAddress);
        verify(addressRepository, times(1)).existsById(1);
        verify(addressRepository, times(1)).save(updatedAddress);
    }

    @Test
    public void testDeleteAddress_Success() {
        when(addressRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(addressRepository).deleteById(anyInt());

        addressService.deleteAddress(1);

        verify(addressRepository, times(1)).existsById(1);
        verify(addressRepository, times(1)).deleteById(1);
    }
}
