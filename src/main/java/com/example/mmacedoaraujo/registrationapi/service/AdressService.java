package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Adress;
import com.example.mmacedoaraujo.registrationapi.repository.AdressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdressService {

    private final AdressRepository adressRepository;

    public List<Adress> listAll() {
        return adressRepository.findAll();
    }
}
