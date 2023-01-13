package com.example.mmacedoaraujo.registrationapi.service.impl;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.example.mmacedoaraujo.registrationapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    public static final String ENDERECO_NAO_ENCONTRADO = "Nenhum endereço com o seguinte id foi encontrado: ";
    private final AddressRepository addressRepository;


    public List<Address> listAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void setAsMainAddress(Long id, User user) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(ENDERECO_NAO_ENCONTRADO + id));
        address.setEnderecoPrincipal(true);
        for (Address addr : user.getAddressList()) {
            addr.setEnderecoPrincipal(false);
            if (Objects.equals(addr.getId(), id)) {
                addr.setEnderecoPrincipal(true);
            }
        }
        addressRepository.save(address);
    }

    @Override
    public Address saveNewAddress(Address address, User user) {
        address.setUserId(user);
        Address savedAddress = addressRepository.save(address);
        setAsMainAddress(savedAddress.getId(), user);
        return addressRepository.findById(address.getId()).orElseThrow(() -> new AddressNotFoundException(ENDERECO_NAO_ENCONTRADO + user.getId()));
    }
}

