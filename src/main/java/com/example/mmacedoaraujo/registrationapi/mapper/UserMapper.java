package com.example.mmacedoaraujo.registrationapi.mapper;

import com.example.mmacedoaraujo.registrationapi.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapRequestToEntity(User requestUser, @MappingTarget User entityUser);
}
