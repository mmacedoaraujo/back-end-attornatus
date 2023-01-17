package com.mmacedoaraujo.registrationapi.controller;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.service.impl.AddressServiceImpl;
import com.mmacedoaraujo.registrationapi.util.EntityCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class AdressControllerTest {
    
    @Mock
    private AddressServiceImpl addressService;
    
    @InjectMocks
    private AdressController adressController;
    
    @BeforeEach
    void setUp() {
        PageImpl<Address> addressPage = new PageImpl<>(List.of(EntityCreator.createAddress()));
        BDDMockito.when(addressService.listAll(ArgumentMatchers.any())).thenReturn(addressPage);
    }

    @Test
    void listAll() {

        Address address = EntityCreator.createAddress();

        Page<Address> addressFromController = adressController.listAll(PageRequest.of(1, 1)).getBody();


        Assertions.assertThat(addressFromController.toList())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(addressFromController)
                .isNotNull()
                .isInstanceOf(Page.class);

        Assertions.assertThat(addressFromController.toList().get(0).getCidade())
                .isEqualTo(address.getCidade());

        Assertions.assertThat(addressFromController.toList().get(0).getId())
                .isEqualTo(address.getId());
    }

    @Test
    void listMainAddress() {

        Address address = EntityCreator.createAddress();

        Page<Address> addressFromController = adressController.listAll(PageRequest.of(1, 1)).getBody();


        Assertions.assertThat(addressFromController.toList())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(addressFromController)
                .isNotNull()
                .isInstanceOf(Page.class);

        Assertions.assertThat(addressFromController.toList().get(0).getCidade())
                .isEqualTo(address.getCidade());

        Assertions.assertThat(addressFromController.toList().get(0).getId())
                .isEqualTo(address.getId());
    }
}