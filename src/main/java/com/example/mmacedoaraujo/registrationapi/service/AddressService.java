package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;

public interface AddressService {

    Address saveAddress(Address address);

    void setAsMainAddress(Long id, User user);

    Address saveNewAddress(Address address, User user);

    void deleteAddress(Long addressId, User user);

    Address findById(Long id);
}
