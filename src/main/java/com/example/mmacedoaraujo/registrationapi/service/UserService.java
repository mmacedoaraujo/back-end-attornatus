package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.requests.UserAddressPostRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<User> listAll(Pageable pageable);

    User findUserById(Long id);

    void updateUser(User userWithChanges);

    User setMainAddress(Long userId, Long addressId);

    List<Address> listAllUserAddresses(Long userId);

    Address getUserMainAddress(Long userId);

    User saveNewAddress(Address address, Long userId);

    void deleteUserAddressById(Long userId, Long addressId);

    User saveUser(UserAddressPostRequestBody userAndAddressEntity);
}
