package com.example.mmacedoaraujo.registrationapi.service.impl;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.example.mmacedoaraujo.registrationapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    public List<Address> listAll() {
        return addressRepository.findAll();
    }

    @Override
    public void setAsMainAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Nenhum endereço com o seguinte id foi encontrado: " + id));
        address.setEnderecoPrincipal(true);

        addressRepository.save(address);
    }

    @Override
    public Address getMainAddressByUserId(Long id) {
        return addressRepository.findMainAddressByUserId(id);
    }
}
