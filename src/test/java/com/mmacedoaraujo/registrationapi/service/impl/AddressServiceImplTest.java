package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.mapper.AddressMapper;
import com.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.mmacedoaraujo.registrationapi.requests.PersonAddressPostRequestBody;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        Address addressWithFalseEnderecoPrincipal = EntityCreator.createAddress();
        PageImpl<Address> addressPage = new PageImpl<>(List.of(EntityCreator.createAddress(), addressWithFalseEnderecoPrincipal));

        BDDMockito.when(addressRepository.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(addressPage);

        BDDMockito.when(addressRepository
                .listOnlyMainAddress(ArgumentMatchers.any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(EntityCreator.createAddress())));

        BDDMockito.when(addressRepository.save(ArgumentMatchers.any(Address.class))).thenReturn(EntityCreator.createAddress());

        BDDMockito.when(addressRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(EntityCreator.createAddress()));
    }

    @Test
    void listAll() {
        Address validAddress = EntityCreator.createAddress();

        Page<Address> addressPage = addressService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(addressPage.toList())
                .isNotEmpty()
                .isNotNull()
                .hasSize(2);

        Assertions.assertThat(addressPage.toList().get(0))
                .isNotNull()
                .isInstanceOf(Address.class)
                .isEqualTo(validAddress);
    }

    @Test
    void listMainAddresses() {

        Page<Address> addressPage = this.addressService.listMainAddresses(PageRequest.of(1, 1));

        Assertions.assertThatCode(() -> this.addressService.listMainAddresses(PageRequest.of(1, 1)))
                .doesNotThrowAnyException();

        Assertions.assertThat(addressPage)
                .hasSize(1)
                .isInstanceOf(Page.class);

        Assertions.assertThat(addressPage.toList().get(0))
                .isEqualTo(EntityCreator.createAddress());

    }

    @Test
    void saveAddress() {
        Address validAddress = EntityCreator.createAddress();

        Address savedAddress = this.addressService.saveAddress(validAddress);

        Assertions.assertThatCode(() -> this.addressService.saveAddress(validAddress)).doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.saveAddress(savedAddress).getId())
                .isEqualTo(validAddress.getId());

        Assertions.assertThat(this.addressService.saveAddress(savedAddress).getLogradouro())
                .isEqualTo(validAddress.getLogradouro());


    }

    @Test
    void setAsMainAddress() {
        AddressServiceImpl addressServiceMock = mock(AddressServiceImpl.class);

        doNothing().when(addressServiceMock).setAsMainAddress(anyLong(), any(Person.class));
        addressServiceMock.setAsMainAddress(1L, EntityCreator.createPerson());

        verify(addressServiceMock, times(1)).setAsMainAddress(1L, EntityCreator.createPerson());
    }

    @Test
    void saveNewAddress() {
        Person validPerson = EntityCreator.createPerson();
        Address validAdress = EntityCreator.createAddress();

        Address savedAddress = this.addressService.saveNewAddress(validAdress, validPerson);

        Assertions.assertThat(savedAddress)
                .isNotNull()
                .isInstanceOf(Address.class)
                .isEqualTo(validAdress);

        Assertions.assertThat(savedAddress.getId())
                .isEqualTo(validAdress.getId());

        Assertions.assertThat(savedAddress.getLogradouro())
                .isEqualTo(validAdress.getLogradouro());

    }

    @Test
    void deleteAddress() {

        AddressServiceImpl addressServiceMock = mock(AddressServiceImpl.class);

        doNothing().when(addressServiceMock).deleteAddress(anyLong(), any(Person.class));

        addressServiceMock.deleteAddress(1L, EntityCreator.createPerson());

        verify(addressServiceMock, times(1)).deleteAddress(1L, EntityCreator.createPerson());
    }

    @Test
    void findById() {
        Person validPerson = EntityCreator.createPerson();
        Address validAdress = EntityCreator.createAddress();

        Address foundAddress = this.addressService.findById(1L);

        Assertions.assertThat(foundAddress)
                .isNotNull()
                .isInstanceOf(Address.class)
                .isEqualTo(validAdress);

        Assertions.assertThat(foundAddress.getId())
                .isEqualTo(validAdress.getId());

        Assertions.assertThat(foundAddress.getLogradouro())
                .isEqualTo(validAdress.getLogradouro());
    }

    @Test
    void separateAddressFromRequest() {
        PersonAddressPostRequestBody testEntity = EntityCreator.createPersonAddressPostRequestBody();

        Address separateAddressFromRequest = this.addressService.separateAddressFromRequest(EntityCreator.createPersonAddressPostRequestBody());

        Assertions.assertThat(separateAddressFromRequest)
                .isNotNull()
                .isInstanceOf(Address.class);

        Assertions.assertThat(separateAddressFromRequest.getLogradouro())
                .isEqualTo(testEntity.getLogradouro());

        Assertions.assertThat(separateAddressFromRequest.getCep())
                .isEqualTo(testEntity.getCep());

        Assertions.assertThat(separateAddressFromRequest.getCidade())
                .isEqualTo(testEntity.getCidade());

        Assertions.assertThat(separateAddressFromRequest.getNumero())
                .isEqualTo(testEntity.getNumero());

    }
}