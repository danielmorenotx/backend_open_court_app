package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.Address;
import com.example.backend_capstone_project.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    // ======= ADD =======
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    // ======= GET ALL =======
    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    // ======= GET BY ID =======
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        try {
            Address address = addressService.getAddressById(id);
            return ResponseEntity.ok(address);
        } catch (NoSuchElementException e) { // requested resource does not exist or is not found
            return ResponseEntity.notFound().build(); // returns 404: Not found, builds final response entity
        }
    }

    // ======= UPDATE =======
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address updatedAddress) {
        try {
            Address address = addressService.updateAddress(id, updatedAddress);
            return ResponseEntity.ok(address);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ======= DELETE =======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build(); // returns 204: No content if successful
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

