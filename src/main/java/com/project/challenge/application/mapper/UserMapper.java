package com.project.challenge.application.mapper;

import com.project.challenge.application.dto.UserDto;
import com.project.challenge.domain.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper{

    Users toEntity(UserDto userDto);
}
