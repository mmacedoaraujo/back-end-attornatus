package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/address")
@RestController
public class AdressController {

    private final AddressRepository addressRepository;

    public AdressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public ResponseEntity<List<Address>> listAll() {
        List<Address> allAddresses = addressRepository.findAll();

        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }
}
