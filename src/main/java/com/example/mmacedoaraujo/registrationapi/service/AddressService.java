package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> listAll() {
        return addressRepository.findAll();
    }

    public Address setAsMainAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Nenhum endereço com o seguinte id foi encontrado: " + id));
        address.setEnderecoPrincipal(true);

        addressRepository.save(address);
        return address;
    }
}
