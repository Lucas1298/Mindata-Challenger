package com.project.challenge.unit;

import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.application.mapper.SpaceshipMapper;
import com.project.challenge.application.services.SpaceshipServiceImpl;
import com.project.challenge.domain.entity.Spaceship;
import com.project.challenge.infrastructure.persistence.repository.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SpaceshipServiceTest {

    @InjectMocks
    private SpaceshipServiceImpl spaceshipService;

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @Spy
    private SpaceshipMapper mapper = Mappers.getMapper(SpaceshipMapper.class);

    @Test
    void shouldDeleteSpaceshipWhenIdExists() {
        Spaceship spaceship = new Spaceship();
        spaceship.setName("Millennium Falcon");
        spaceship.setLength(5);
        spaceship.setCrewCapacity(5);
        spaceship.setPropulsionType("Hyperdrive");
        spaceship.setDescription("A fast, modified freighter known for its speed and versatility.");

        Mockito.when(spaceshipRepository.findById(1)).thenReturn(Optional.of(spaceship));

        spaceshipService.delete(1);

        Mockito.verify(spaceshipRepository, Mockito.times(1)).delete(spaceship);
    }


    @Test
    void shouldReturnSpaceshipWhenIdExists() {
        Spaceship spaceship = new Spaceship();
        spaceship.setName("Millennium Falcon");
        spaceship.setLength(5);
        spaceship.setCrewCapacity(5);
        spaceship.setPropulsionType("Hyperdrive");
        spaceship.setDescription("A fast, modified freighter known for its speed and versatility.");

        Mockito.when(spaceshipRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(spaceship));

        var spaceshipResult = spaceshipService.findSpaceship(1);

        assertEquals(spaceship.getName(), spaceshipResult.name());
        assertEquals(spaceship.getDescription(), spaceshipResult.description());
    }

    @Test
    void shouldSaveSpaceshipSuccessfully() {
        SpaceshipDTO spaceshipDto = new SpaceshipDTO(
                "Millennium Falcon",
                5,
                "34.75",
                "Hyperdrive",
                "A fast, modified freighter known for its speed and versatility."
        );

        Spaceship spaceship = new Spaceship();
        spaceship.setName("Millennium Falcon");
        spaceship.setLength(5);
        spaceship.setCrewCapacity(5);
        spaceship.setPropulsionType("Hyperdrive");
        spaceship.setDescription("A fast, modified freighter known for its speed and versatility.");

        Mockito.when(spaceshipRepository.save(Mockito.any()))
                .thenReturn(spaceship);

        var spaceshipResult = spaceshipService.save(spaceshipDto);

        assertEquals(spaceship.getName(), spaceshipResult.name());
        assertEquals(spaceship.getDescription(), spaceshipResult.description());
    }

    @Test
    void shouldUpdateSpaceshipWhenIdExists(){
        SpaceshipDTO spaceshipDto = new SpaceshipDTO(
                "Millennium Falcon",
                5,
                "34.75",
                "Hyperdrive",
                "A fast, modified freighter known for its speed and versatility."
        );

        Spaceship spaceship = new Spaceship();
        spaceship.setName("Millennium Falcon");
        spaceship.setLength(5);
        spaceship.setCrewCapacity(5);
        spaceship.setPropulsionType("Hyperdrive");
        spaceship.setDescription("A fast, modified freighter known for its speed and versatility.");

        Mockito.when(spaceshipRepository.findById(Mockito.any()))
                .thenReturn(Optional.ofNullable(spaceship));


        Mockito.when(spaceshipRepository.save(Mockito.any()))
                .thenReturn(spaceship);

        var spaceshipResult = spaceshipService.update(1, spaceshipDto);

        assertEquals(spaceshipDto.name(), spaceshipResult.name());
        assertEquals(spaceshipDto.description(), spaceshipResult.description());
    }
}