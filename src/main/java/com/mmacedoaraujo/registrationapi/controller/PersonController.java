package com.mmacedoaraujo.registrationapi.controller;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import com.mmacedoaraujo.registrationapi.service.impl.PersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceImpl personServiceImpl;

    private final AddressServiceImpl addressServiceImpl;

    @GetMapping
    @Operation(summary = "List all persons registered", tags = {"Person"})
    public ResponseEntity<Page<Person>> listAll(@ParameterObject Pageable pageable) {
        Page<Person> users = personServiceImpl.listAll(pageable);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) {
        Person personFoundById = personServiceImpl.findPersonById(id);

        return new ResponseEntity<>(personFoundById, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    @Operation(summary = "List all users found by name specified", description = "The default size is 20, use the parameter size to change the default value",
    tags = {"Person"})
    public ResponseEntity<Page<Person>> findByName(Pageable pageable, @PathVariable String name) {
        Page<Person> userFoundByUsername = personServiceImpl.findPersonByName(pageable, name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }

    @GetMapping("/getMainAddress/{userId}")
    public ResponseEntity<Address> mainAddress(@PathVariable Long userId) {
        Address mainAddress = personServiceImpl.getPersonMainAddress(userId);
        return new ResponseEntity<>(mainAddress, HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses/{userId}")
    public ResponseEntity<List<Address>> allAddresses(@PathVariable Long userId) {
        List<Address> addresses = personServiceImpl.listAllPersonAddresses(userId);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/saveNewUser")
    public ResponseEntity<Person> saveNewPerson(@RequestBody PersonAddressPostRequestBody userAndAddressEntity) {
        Person savedPerson = personServiceImpl.savePerson(userAndAddressEntity);

        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PostMapping("/addNewAddress/{userId}")
    public ResponseEntity<Person> addNewAddress(@RequestBody Address address, @PathVariable Long userId) {
        Person person = personServiceImpl.saveNewAddress(address, userId);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteUserAddress/{userId}/{addressId}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        personServiceImpl.deletePersonAddressById(userId, addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody Person person) {
        personServiceImpl.updatePerson(person);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/setMainAddress")
    public ResponseEntity<Person> setMainAddress(@RequestParam Long userId, @RequestParam Long addressId) {
        Person person = personServiceImpl.setMainAddress(userId, addressId);

        return new ResponseEntity<>(person, HttpStatus.NO_CONTENT);
    }
}
