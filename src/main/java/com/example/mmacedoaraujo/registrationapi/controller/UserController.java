package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.requests.UserAddressPostRequestBody;
import com.example.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import com.example.mmacedoaraujo.registrationapi.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final AddressServiceImpl addressServiceImpl;

    @GetMapping
    public ResponseEntity<Page<User>> listAll(Pageable pageable) {
        Page<User> users = userServiceImpl.listAll(pageable);

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User userFoundById = userServiceImpl.findUserById(id);

        return new ResponseEntity<>(userFoundById, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Page<User>> findByName(Pageable pageable, @PathVariable String name) {
        Page<User> userFoundByUsername = userServiceImpl.findUserByName(pageable, name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }

    @GetMapping("/getMainAddress/{userId}")
    public ResponseEntity<Address> mainAddress(@PathVariable Long userId) {
        Address mainAddress = userServiceImpl.getUserMainAddress(userId);
        return new ResponseEntity<>(mainAddress, HttpStatus.OK);
    }

    @GetMapping("/getAllAddresses/{userId}")
    public ResponseEntity<List<Address>> allAddresses(@PathVariable Long userId) {
        List<Address> addresses = userServiceImpl.listAllUserAddresses(userId);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/saveNewUser")
    public ResponseEntity<User> saveNewUser(@RequestBody UserAddressPostRequestBody userAndAddressEntity) {
        User savedUser = userServiceImpl.saveUser(userAndAddressEntity);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/addNewAddress/{userId}")
    public ResponseEntity<User> addNewAddress(@RequestBody Address address, @PathVariable Long userId) {
        User user = userServiceImpl.saveNewAddress(address, userId);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteUserAddress/{userId}/{addressId}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        userServiceImpl.deleteUserAddressById(userId, addressId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userServiceImpl.updateUser(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/setMainAddress")
    public ResponseEntity<User> setMainAddress(@RequestParam Long userId, @RequestParam Long addressId) {
        User user = userServiceImpl.setMainAddress(userId, addressId);

        return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
    }
}
