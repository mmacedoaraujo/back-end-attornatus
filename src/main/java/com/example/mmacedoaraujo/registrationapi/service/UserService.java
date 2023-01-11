package com.example.mmacedoaraujo.registrationapi.service;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import com.example.mmacedoaraujo.registrationapi.domain.User;
import com.example.mmacedoaraujo.registrationapi.exceptions.UserNotFoundExeption;
import com.example.mmacedoaraujo.registrationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressService addressService;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundExeption("Não foi possível encontrar um usuário com o seguinte id: " + id));
    }

    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name.toUpperCase());
    }

    public User setMainAddress(Long userId, Long addressId) {
        User userFounById = findUserById(userId);
        for (Address x : userFounById.getAddressList()) {
            x.setEnderecoPrincipal(false);
        }
        addressService.setAsMainAddress(addressId);
        return userFounById;
    }

}
