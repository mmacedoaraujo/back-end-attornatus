package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;

import java.util.List;

public interface UserService {

    List<User> listAll();

    User findUserById(Long id);

    List<User> findUserByName(String name);

    void updateUser(User userWithChanges);

    User setMainAddress(Long userId, Long addressId);

    List<Address> listAllUserAddresses(Long userId);

    Address getUserMainAddress(Long userId);
}
