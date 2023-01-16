package com.mmacedoaraujo.registrationapi.service;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;

public interface AddressService {

    Address saveAddress(Address address);

    void setAsMainAddress(Long id, Person person);

    Address saveNewAddress(Address address, Person person);

    void deleteAddress(Long addressId, Person person);

    Address findById(Long id);
}
