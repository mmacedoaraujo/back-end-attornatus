package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        List<User> users = userService.listAll();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User userFoundById = userService.findUserById(id);

        return new ResponseEntity<>(userFoundById, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable String name) {
        List<User> userFoundByUsername = userService.findUserByName(name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }

    @PutMapping("/mainaddress")
    public ResponseEntity<User> setMainAddress(@RequestParam Long userId, @RequestParam Long addressId) {
        User user = userService.setMainAddress(userId, addressId);

        return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
    }
}
