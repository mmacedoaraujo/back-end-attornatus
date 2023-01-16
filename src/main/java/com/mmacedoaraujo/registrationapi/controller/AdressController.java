package com.mmacedoaraujo.registrationapi.controller;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/address")
@RestController
@RequiredArgsConstructor
public class AdressController {

    private final AddressServiceImpl addressService;

    @GetMapping
    public ResponseEntity<Page<Address>> listAll(Pageable pageable) {
        Page<Address> allAddresses = addressService.listAll(pageable);

        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }

    @GetMapping("/mainAddress")
    public ResponseEntity<Page<Address>> listMainAddress(Pageable pageable) {
        Page<Address> addresses = addressService.listMainAddresses(pageable);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
}
