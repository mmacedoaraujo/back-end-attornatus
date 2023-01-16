package com.mmacedoaraujo.registrationapi.mapper;

import com.mmacedoaraujo.registrationapi.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Person mapRequestToEntity(Person requestPerson, @MappingTarget Person entityPerson);
}
