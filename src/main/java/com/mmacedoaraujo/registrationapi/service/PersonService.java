package com.mmacedoaraujo.registrationapi.service;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.UserAddressPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    Page<Person> listAll(Pageable pageable);

    Person findPersonById(Long id);

    void updatePerson(Person personWithChanges);

    Person setMainAddress(Long userId, Long addressId);

    List<Address> listAllPersonAddresses(Long userId);

    Address getPersonMainAddress(Long userId);

    Person saveNewAddress(Address address, Long userId);

    void deletePersonAddressById(Long userId, Long addressId);

    Person savePerson(UserAddressPostRequestBody userAndAddressEntity);
}
