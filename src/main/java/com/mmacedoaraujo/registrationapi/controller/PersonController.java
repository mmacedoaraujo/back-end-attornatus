package com.mmacedoaraujo.registrationapi.controller;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.requests.PersonPutRequestBody;
import com.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import com.mmacedoaraujo.registrationapi.service.impl.PersonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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
@CrossOrigin
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
    @Operation(summary = "Find a person by it's id",
            description = "It will search for the id and if it can't find any person with that id, it will throw an exception.",
            tags = {"Person"})
    public ResponseEntity<Person> findPersonById(@PathVariable Long id) {
        Person personFoundById = personServiceImpl.findPersonById(id);

        return new ResponseEntity<>(personFoundById, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    @Operation(summary = "List all users found by a specified name", description = "The default size is 10, use the parameter size to change the default value",
            tags = {"Person"})
    public ResponseEntity<Page<Person>> findByName(@ParameterObject Pageable pageable, @PathVariable String name) {
        Page<Person> userFoundByUsername = personServiceImpl.findPersonByName(pageable, name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }

    @GetMapping("/getMainAddress/{personId}")
    @Operation(summary = "It will bring the main address of that person",
            description = "Each person has only one main address, so this method will bring it. If the specified id is not valid, it will throw an exception.",
            tags = {"Person"})
    public ResponseEntity<Address> mainAddress(@PathVariable Long personId) {
        Address mainAddress = personServiceImpl.getPersonMainAddress(personId);
        return new ResponseEntity<>(mainAddress, HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses/{personId}")
    @Operation(summary = "List all person's addresses",
            description = "It will bring all the address a person has already registered",
            tags = {"Person"})
    public ResponseEntity<List<Address>> allAddresses(@PathVariable Long personId) {
        List<Address> addresses = personServiceImpl.listAllPersonAddresses(personId);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/saveNewPerson")
    @Operation(summary = "Saves a new person",
            description = "A person is always registered with an address and that address will be automatically set as his main address." +
                    " !IMPORTANT! The date should be in this format: dd-mm-yyyy !IMPORTANT!",
            tags = {"Person"})
    public ResponseEntity<Person> saveNewPerson(@RequestBody PersonAddressPostRequestBody personAndAddressEntity) {
        Person savedPerson = personServiceImpl.savePerson(personAndAddressEntity);

        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PostMapping("/addNewAddress/{personId}")
    @Operation(summary = "Add a new address to a specified person",
            description = "This method will add a new address to a person, using it's id to find him on the database",
            tags = {"Person"})
    public ResponseEntity<Person> addNewAddress(@RequestBody Address address, @PathVariable Long personId) {
        Person person = personServiceImpl.saveNewAddress(address, personId);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePersonAddress/{personId}/{addressId}")
    @Operation(summary = "Deletes a person's address from the database",
            description = "Receives a person id and one address id, if the person found has one address with the specified address id, the method will delete it",
            tags = {"Person"})
    public ResponseEntity<Void> deletePersonAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        personServiceImpl.deletePersonAddressById(personId, addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    @Operation(summary = "Update person's information",
            description = "This method will gather the received information and update the user on the database based on the id sent on the request",
            tags = {"Person"})
    public ResponseEntity<Void> updateUser(@RequestBody PersonPutRequestBody person) {
        personServiceImpl.updatePerson(person);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/setMainAddress")
    @Operation(summary = "Modify the status of an address to main address",
            description = "This method will change the property enderecoPrincipal to true based on the address id, note that all the others addresses will receive false on the property",
            tags = {"Person"})
    public ResponseEntity<Person> setMainAddress(@RequestParam Long userId, @RequestParam Long addressId) {
        Person person = personServiceImpl.setMainAddress(userId, addressId);

        return new ResponseEntity<>(person, HttpStatus.NO_CONTENT);
    }
}
