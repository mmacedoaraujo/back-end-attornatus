package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;

public interface AddressService {

    void setAsMainAddress(Long id);

    Address getMainAddressByUserId(Long id);
}
