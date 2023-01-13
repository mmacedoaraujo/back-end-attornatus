package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;

public interface AddressService {

    Address saveAddress(Address address);

    void setAsMainAddress(Long id, User userId);

    Address saveNewAddress(Address address, User userId);
}
