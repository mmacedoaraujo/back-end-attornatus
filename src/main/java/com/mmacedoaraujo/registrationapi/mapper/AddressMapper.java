package com.mmacedoaraujo.registrationapi.mapper;

import com.mmacedoaraujo.registrationapi.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    void mapRequestToEntity(Address requestAddress, @MappingTarget Address entityAddress);
}
