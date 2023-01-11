package com.example.mmacedoaraujo.registrationapi.controller;

import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User userFoundById = userService.findUserById(id);

        return new ResponseEntity<>(userFoundById, HttpStatus.OK);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable String name) {
        List<User> userFoundByUsername = userService.findUserByName(name);

        return new ResponseEntity<>(userFoundByUsername, HttpStatus.OK);
    }
}
