package com.project.challenge.application.services;

import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.application.exception.ApiException;
import com.project.challenge.application.mapper.SpaceshipMapper;
import com.project.challenge.domain.entity.Spaceship;
import com.project.challenge.domain.port.spaceship.SpaceshipService;
import com.project.challenge.infrastructure.persistence.SpaceshipSpecification;
import com.project.challenge.infrastructure.persistence.repository.SpaceshipRepository;
import com.project.challenge.infrastructure.rest.controller.spaceship.dto.SpaceshipResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private SpaceshipMapper spaceshipMapper;


    @Override
    public SpaceshipResponseDTO save(SpaceshipDTO request) {
        var spaceship = spaceshipMapper.toEntity(request);
        spaceship = spaceshipRepository.save(spaceship);
        return spaceshipMapper.toResponse(spaceship);
    }

    @CachePut(value = "spaceship", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public SpaceshipResponseDTO update(Integer id, SpaceshipDTO spaceshipRequest)  {
        var spaceship = getSpaceshipById(id);
        var spaceshipUpdated = spaceshipMapper.updateEntity(spaceship, spaceshipRequest);
        spaceshipUpdated = spaceshipRepository.save(spaceshipUpdated);
        return spaceshipMapper.toResponse(spaceshipUpdated);
    }

    @Override
    @Cacheable(value = "spaceship", key = "#id")
    public SpaceshipResponseDTO findSpaceship(Integer id) {
        var spaceship = getSpaceshipById(id);
        return spaceshipMapper.toResponse(spaceship);
    }

    @Override
    public Page<SpaceshipResponseDTO> findAll(Pageable pageable, String filters) {
        Specification<Spaceship> spec = Specification.where(null);

        if (filters != null) {
            spec = SpaceshipSpecification.filterByParams(convertStringToMap(filters));
        }

        var spaceships = spaceshipRepository.findAll(spec, pageable);
        return spaceships.map(spaceshipMapper::toResponse);
    }

    @CacheEvict(value = "spaceship", key = "#id")
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(Integer id) {
        var spaceship = getSpaceshipById(id);
        spaceshipRepository.delete(spaceship);
    }


    private Spaceship getSpaceshipById(Integer id){
        return spaceshipRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,  "Spaceship not found with ID: ".concat(String.valueOf(id))));
    }


    private static Map<String, String> convertStringToMap(String input) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = input.split(";");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                map.put(key, value);
            }
        }
        return map;
    }
}
