package com.mmacedoaraujo.registrationapi.service.impl;

import com.mmacedoaraujo.registrationapi.domain.Address;
import com.mmacedoaraujo.registrationapi.domain.Person;
import com.mmacedoaraujo.registrationapi.exceptions.AddressNotFoundException;
import com.mmacedoaraujo.registrationapi.mapper.AddressMapper;
import com.mmacedoaraujo.registrationapi.repository.AddressRepository;
import com.mmacedoaraujo.registrationapi.requests.UserAddressPostRequestBody;
import com.mmacedoaraujo.registrationapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    public static final String ENDERECO_NAO_ENCONTRADO = "Nenhum endereço foi encontrado com seguinte id: ";
    private final AddressRepository addressRepository;


    public Page<Address> listAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Page<Address> listMainAddresses(Pageable pageable) {

        return addressRepository.listOnlyMainAddress(pageable);
    }

    @Override
    public Address saveAddress(Address requestAddress) {
        Address newAddress = new Address();
        AddressMapper.INSTANCE.mapRequestToEntity(requestAddress, newAddress);
        return addressRepository.save(newAddress);
    }

    @Override
    public void setAsMainAddress(Long id, Person person) {
        Address address = findById(id);
        address.setEnderecoPrincipal(true);
        if (person.getAddressList().isEmpty()) {
            person.getAddressList().add(address);
        } else {
            for (Address addr : person.getAddressList()) {
                addr.setEnderecoPrincipal(false);
                if (Objects.equals(addr.getId(), id)) {
                    addr.setEnderecoPrincipal(true);
                }
            }
        }
        addressRepository.save(address);
    }

    @Override
    public Address saveNewAddress(Address address, Person person) {
        address.setPersonId(person);
        Address savedAddress = addressRepository.save(address);
        setAsMainAddress(savedAddress.getId(), person);
        return findById(address.getId());
    }

    @Override
    public void deleteAddress(Long addressId, Person person) {
        findById(addressId);
        if (findById(addressId).isEnderecoPrincipal()) {
            //No caso do endereço a ser deletado seja um endereço principal
            addressRepository.deleteById(addressId);
            if (!person.getAddressList().isEmpty()) {
                defineMainAddressAfterDelete(person);
            }
        } else {
            addressRepository.deleteById(addressId);
        }

    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(ENDERECO_NAO_ENCONTRADO + id));

    }

    private void defineMainAddressAfterDelete(Person person) {
        Address address = person.getAddressList().get(0);
        setAsMainAddress(address.getId(), person);
    }

    public Address separateAddressFromRequest(UserAddressPostRequestBody userAndAddressEntity) {
        return new Address(null, userAndAddressEntity.getLogradouro(),
                userAndAddressEntity.getCep(),
                userAndAddressEntity.getNumero(),
                userAndAddressEntity.getCidade(),
                userAndAddressEntity.isEnderecoPrincipal(),
                userAndAddressEntity.getPersonId());
    }


}

