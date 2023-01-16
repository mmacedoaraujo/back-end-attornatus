package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.exceptions.PersonNotFoundExeption;
import com.mmacedoaraujo.registrationapi.mapper.UserMapper;
import com.mmacedoaraujo.registrationapi.repository.PersonRepository;
import com.mmacedoaraujo.registrationapi.requests.UserAddressPostRequestBody;
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
    public void updatePerson(Person requestPerson) {
        Person entityPerson = findPersonById(requestPerson.getId());
        Person updatedPerson = UserMapper.INSTANCE.mapRequestToEntity(requestPerson, entityPerson);

        personRepository.save(updatedPerson);

    }

    @Override
    public Person setMainAddress(Long userId, Long addressId) {
        Person personFounById = findPersonById(userId);
        addressServiceImpl.setAsMainAddress(addressId, personFounById);
        return personFounById;
    }

    @Override
    public List<Address> listAllPersonAddresses(Long userId) {
        return findPersonById(userId).getAddressList();
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
    public Person saveNewAddress(Address address, Long userId) {
        Person personFoundById = findPersonById(userId);
        addressServiceImpl.saveNewAddress(address, personFoundById);

        return personFoundById;
    }

    @Override
    public void deletePersonAddressById(Long userId, Long addressId) {
        Person personFoundById = findPersonById(userId);
        addressServiceImpl.deleteAddress(addressId, personFoundById);
    }

    @Override
    public Person savePerson(UserAddressPostRequestBody userAndAddressEntity) {
        Person newPerson = separateEnityUser(userAndAddressEntity);
        Address addressFromRequest = addressServiceImpl.separateAddressFromRequest(userAndAddressEntity);
        Person savedPerson = personRepository.save(newPerson);
        savedPerson.getAddressList().add(addressFromRequest);
        saveNewAddress(addressFromRequest, savedPerson.getId());

        return savedPerson;

    }

    private Person separateEnityUser(UserAddressPostRequestBody userAndAddressEntity) {
        return new Person(null, userAndAddressEntity.getName(), userAndAddressEntity.getBirthdate(), new ArrayList<>());
    }

}
