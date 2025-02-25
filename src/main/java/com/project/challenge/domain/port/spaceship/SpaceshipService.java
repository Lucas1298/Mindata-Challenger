package com.project.challenge.domain.port.spaceship;

import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.infrastructure.rest.controller.spaceship.dto.SpaceshipResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipService {

    SpaceshipResponseDTO save(SpaceshipDTO request);

    SpaceshipResponseDTO findSpaceship(Integer id);

    Page<SpaceshipResponseDTO> findAll(Pageable pageable, String filters);

    void delete(Integer id);

    SpaceshipResponseDTO update(Integer id, SpaceshipDTO spaceshipRequest);
}
