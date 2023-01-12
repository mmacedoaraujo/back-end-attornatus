package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import com.example.mmacedoaraujo.registrationapi.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<User>> listAll() {
        List<User> users = userServiceImpl.listAll();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User userFoundById = userServiceImpl.findUserById(id);

        return new ResponseEntity<>(userFoundById, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable String name) {
        List<User> userFoundByUsername = userServiceImpl.findUserByName(name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }

    @GetMapping("/getMainAddress/{userId}")
    public ResponseEntity<Address> mainAddress(@PathVariable Long userId) {
        Address mainAddress = addressServiceImpl.getMainAddressByUserId(userId);
        return new ResponseEntity<>(mainAddress, HttpStatus.OK);
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
