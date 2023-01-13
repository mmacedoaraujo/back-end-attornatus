package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/address")
@RestController
@RequiredArgsConstructor
public class AdressController {

    private final AddressServiceImpl addressService;

    @GetMapping
    public ResponseEntity<List<Address>> listAll() {
        List<Address> allAddresses = addressService.listAll();

        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }

    @GetMapping("/mainAddress")
    public ResponseEntity<List<Address>> listMainAddress() {
        List<Address> addresses = addressService.listMainAddresses();

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
}
