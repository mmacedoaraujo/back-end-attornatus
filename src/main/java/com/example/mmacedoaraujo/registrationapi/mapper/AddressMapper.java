package com.example.mmacedoaraujo.registrationapi.mapper;

import com.example.mmacedoaraujo.registrationapi.domain.Address;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address mapRequestToEntity(Address requestAddress, @MappingTarget Address entityAddress);
}
