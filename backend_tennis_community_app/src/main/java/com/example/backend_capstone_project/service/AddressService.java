package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Address;
import com.example.backend_capstone_project.repository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {

    @Autowired
    IAddressRepository addressRepository;

    // ======= ADD ADDRESS =======
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    // ======= GET ALL ADDRESSES =======
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // ======= GET BY ID =======
    public Address getAddressById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    // ======= UPDATE =======
    public Address updateAddress(Integer id, Address updatedAddress) {
        if (!addressRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        updatedAddress.setId(id); // Ensure the ID of the updated address matches the ID in the path
        return addressRepository.save(updatedAddress);
    }

    // ======= DELETE =======
    public void deleteAddress(Integer id) {
        if (!addressRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        addressRepository.deleteById(id);
    }
}

