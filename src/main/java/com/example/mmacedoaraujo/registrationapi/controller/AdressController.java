package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.Adress;
import com.example.mmacedoaraujo.registrationapi.repository.AdressRepository;
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

    private final AdressRepository adressRepository;

    @GetMapping
    public ResponseEntity<List<Adress>> listAll() {
        List<Adress> allAdresses = adressRepository.findAll();

        return new ResponseEntity<>(allAdresses, HttpStatus.OK);
    }
}
