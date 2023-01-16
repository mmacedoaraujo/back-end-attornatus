package com.mmacedoaraujo.registrationapi.mapper;

import com.mmacedoaraujo.registrationapi.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Person mapRequestToEntity(Person requestPerson, @MappingTarget Person entityPerson);
}
