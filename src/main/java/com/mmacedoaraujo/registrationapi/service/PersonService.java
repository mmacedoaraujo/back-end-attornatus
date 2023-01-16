package com.mmacedoaraujo.registrationapi.service;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.requests.PersonPutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    Page<Person> listAll(Pageable pageable);

    Person findPersonById(Long id);

    void updatePerson(PersonPutRequestBody personWithChanges);

    Person setMainAddress(Long personId, Long addressId);

    List<Address> listAllPersonAddresses(Long personId);

    Address getPersonMainAddress(Long userId);

    Person saveNewAddress(Address address, Long personId);

    void deletePersonAddressById(Long personId, Long addressId);

    Person savePerson(PersonAddressPostRequestBody userAndAddressEntity);
}
