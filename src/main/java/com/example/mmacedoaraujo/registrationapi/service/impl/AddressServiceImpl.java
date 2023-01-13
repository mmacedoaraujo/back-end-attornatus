package com.example.mmacedoaraujo.registrationapi.service.impl;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.example.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.example.mmacedoaraujo.registrationapi.service.AddressService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    public static final String ENDERECO_NAO_ENCONTRADO = "Nenhum endereço foi encontrado com seguinte id: ";
    private final AddressRepository addressRepository;


    public Page<Address> listAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void setAsMainAddress(Long id, User user) {
        Address address = findById(id);
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
        return findById(address.getId());
    }

    @Override
    public void deleteAddress(Long addressId, User user) {
        findById(addressId);
        if (findById(addressId).isEnderecoPrincipal()) {
            //No caso do endereço a ser deletado seja um endereço principal
            addressRepository.deleteById(addressId);
            if (!user.getAddressList().isEmpty()) {
                defineMainAddressAfterDelete(user);
            }
        } else {
            addressRepository.deleteById(addressId);
        }

    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(ENDERECO_NAO_ENCONTRADO + id));

    }

    private void defineMainAddressAfterDelete(User user) {
        Address address = user.getAddressList().get(0);
        setAsMainAddress(address.getId(), user);
    }

    public Page<Address> listMainAddresses(Pageable pageable) {

        return addressRepository.listOnlyMainAddress(pageable);
    }
}

