package com.project.challenge.application.mapper;

import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.domain.entity.Spaceship;
import com.project.challenge.infrastructure.rest.controller.spaceship.dto.SpaceshipResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpaceshipMapper {

    Spaceship toEntity(SpaceshipDTO request);

    SpaceshipResponseDTO toResponse(Spaceship spaceship);

    Spaceship updateEntity(@MappingTarget Spaceship spaceship, SpaceshipDTO spaceshipDTO);

}
