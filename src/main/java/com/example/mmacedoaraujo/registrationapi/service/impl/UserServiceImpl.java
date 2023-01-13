package com.example.mmacedoaraujo.registrationapi.service.impl;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExeption;
import com.example.mmacedoaraujo.registrationapi.mapper.UserMapper;
import com.example.mmacedoaraujo.registrationapi.repository.UserRepository;
import com.example.mmacedoaraujo.registrationapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressServiceImpl addressServiceImpl;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundExeption("Não foi possível encontrar um usuário com o seguinte id: " + id));
    }

    @Override
    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name.toUpperCase());
    }

    @Override
    public void updateUser(User userWithChanges) {
        User alreadyRegisteredUser = findUserById(userWithChanges.getId());
        User updatedUser = UserMapper.INSTANCE.updateUser(userWithChanges, alreadyRegisteredUser);

        userRepository.save(updatedUser);

    }

    @Override
    public User setMainAddress(Long userId, Long addressId) {
        User userFounById = findUserById(userId);
        addressServiceImpl.setAsMainAddress(addressId, userFounById);
        return userFounById;
    }

    @Override
    public List<Address> listAllUserAddresses(Long userId) {
        return findUserById(userId).getAddressList();
    }

    @Override
    public Address getUserMainAddress(Long userId) {
        return findUserById(userId)
                .getAddressList()
                .stream()
                .filter(address -> address.isEnderecoPrincipal())
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public User saveNewAddress(Address address, Long userId) {
        User userFoundById = findUserById(userId);
        addressServiceImpl.saveNewAddress(address, userFoundById);

        return userFoundById;
    }

}
