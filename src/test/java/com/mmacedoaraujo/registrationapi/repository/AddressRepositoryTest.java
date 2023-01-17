package com.mmacedoaraujo.registrationapi.repository;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.util.EntityCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for repository test")
public class AddressRepositoryTest {
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void savePerson() {
        Address address = EntityCreator.createAddress();
        Address savedAddress = this.addressRepository.save(address);

        Assertions.assertThat(savedAddress).isNotNull();
        Assertions.assertThat(savedAddress.getId()).isNotNull();
        Assertions.assertThat(savedAddress.getLogradouro()).isNotNull().isEqualTo(address.getLogradouro());
    }

    @Test
    void saveUpdatesPerson() {
        Address addressToBeUpdated = EntityCreator.createAddress();

        Address savedPerson = this.addressRepository.save(addressToBeUpdated);

        savedPerson.setCidade("Repository test");

        Address updatedPerson = this.addressRepository.save(savedPerson);

        Assertions.assertThat(updatedPerson).isNotNull();
        Assertions.assertThat(updatedPerson.getId()).isNotNull();
        Assertions.assertThat(updatedPerson).isEqualTo(savedPerson);
    }

    @Test
    void delete() {
        Address address = EntityCreator.createAddress();

        Address savedPerson = this.addressRepository.save(address);

        this.addressRepository.delete(savedPerson);

        Optional<Address> addressFoundById = this.addressRepository.findById(savedPerson.getId());

        Assertions.assertThat(addressFoundById).isEmpty();
    }

    @Test
    void listOnlyMainAddresses() {
        Address addressFalse = EntityCreator.createAddress();
        addressFalse.setEnderecoPrincipal(false);
        Address addressTrue = EntityCreator.createAddress();
        addressFalse.setEnderecoPrincipal(true);


        addressRepository.saveAll(Arrays.asList(addressFalse, addressTrue));

        Page<Address> addressPage = addressRepository.listOnlyMainAddress(PageRequest.of(1, 1));

        Assertions.assertThat(addressPage.toList())
                .hasSize(1)
                .isNotEmpty()
                .isNotNull();

        Assertions.assertThat(addressPage.toList().get(0))
                .isInstanceOf(Address.class);

        Assertions.assertThat(addressPage.toList().get(0).isEnderecoPrincipal())
                .isTrue();

    }
}
