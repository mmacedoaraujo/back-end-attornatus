package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.exceptions.PersonNotFoundExeption;
import com.mmacedoaraujo.registrationapi.mapper.UserMapper;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.requests.PersonPutRequestBody;
import com.mmacedoaraujo.registrationapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AddressServiceImpl addressServiceImpl;

    @Override
    public Page<Person> listAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person findPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(
                        () -> new PersonNotFoundExeption("Não foi possível encontrar um usuário com o seguinte id: " + id));
    }

    public Page<Person> findPersonByName(Pageable pageable, String name) {
        return personRepository.findPersonByName(pageable, name.toUpperCase());
    }

    @Override
    public void updatePerson(PersonPutRequestBody requestPerson) {
        Person entityPerson = findPersonById(requestPerson.getId());
        Person updatedPerson = UserMapper.INSTANCE.mapRequestToEntity(requestPerson, entityPerson);
        updatedPerson.setId(entityPerson.getId());

        personRepository.save(updatedPerson);

    }

    @Override
    public Person setMainAddress(Long userId, Long addressId) {
        Person personFounById = findPersonById(userId);
        addressServiceImpl.setAsMainAddress(addressId, personFounById);
        return personFounById;
    }

    @Override
    public List<Address> listAllPersonAddresses(Long personId) {
        return findPersonById(personId).getAddressList();
    }

    @Override
    public Address getPersonMainAddress(Long userId) {
        return findPersonById(userId)
                .getAddressList()
                .stream()
                .filter(address -> address.isEnderecoPrincipal())
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public Person saveNewAddress(Address address, Long personId) {
        Person personFoundById = findPersonById(personId);
        addressServiceImpl.saveNewAddress(address, personFoundById);

        return personFoundById;
    }

    @Override
    public void deletePersonAddressById(Long userId, Long addressId) {
        Person personFoundById = findPersonById(userId);
        addressServiceImpl.deleteAddress(addressId, personFoundById);
    }

    @Override
    public Person savePerson(PersonAddressPostRequestBody userAndAddressEntity) {
        Person newPerson = separateEntityFromPersonAddressRequestBody(userAndAddressEntity);
        Address addressFromRequest = addressServiceImpl.separateAddressFromRequest(userAndAddressEntity);
        Person savedPerson = personRepository.save(newPerson);
        saveNewAddress(addressFromRequest, savedPerson.getId());

        return savedPerson;

    }

    private Person separateEntityFromPersonAddressRequestBody(PersonAddressPostRequestBody userAndAddressEntity) {
        return new Person(null, userAndAddressEntity.getName(), userAndAddressEntity.getBirthdate(), new ArrayList<>());
    }

}
