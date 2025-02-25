package com.project.challenge.integracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.domain.entity.Spaceship;
import com.project.challenge.infrastructure.persistence.repository.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@EmbeddedKafka(partitions = 1, controlledShutdown = true)
public class SpaceshipControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    private final String spaceshipPath = "/v1.0/spaceship";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Spaceship spaceship = new Spaceship();
        spaceship.setName("Millennium Falcon");
        spaceship.setLength(5);
        spaceship.setCrewCapacity(5);
        spaceship.setPropulsionType("Hyperdrive");
        spaceship.setDescription("A fast, modified freighter known for its speed and versatility.");

        spaceshipRepository.save(spaceship);
    }


    @Test
    public void createSpaceship_ShouldReturnCreatedResponse_WhenRequestIsValid() throws Exception {
        SpaceshipDTO spaceship = new SpaceshipDTO(
                "Millennium Falcon",
                5,
                "34.75",
                "Hyperdrive",
                "A fast, modified freighter known for its speed and versatility."
        );

        this.mockMvc.perform(
                        post(spaceshipPath)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(spaceship)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo(spaceship.name())))
                .andExpect(jsonPath("$.crew_capacity", equalTo(spaceship.crewCapacity())))
                .andExpect(jsonPath("$.length", equalTo(spaceship.length())))
                .andExpect(jsonPath("$.propulsion_type", equalTo(spaceship.propulsionType())))
                .andExpect(jsonPath("$.description", equalTo(spaceship.description())));
    }


    @Test
    public void findSpaceship_ShouldReturnSpaceship_WhenIdExists() throws Exception {
        this.mockMvc.perform(
                        get(spaceshipPath.concat("/{id}"), 2)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo("Millennium Falcon")))
                .andExpect(jsonPath("$.crew_capacity", equalTo(5)))
                .andExpect(jsonPath("$.propulsion_type", equalTo("Hyperdrive")))
                .andExpect(jsonPath("$.description", equalTo("A fast, modified freighter known for its speed and versatility.")));
    }

    @Test
    public void findSpaceship_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        this.mockMvc.perform(
                        get(spaceshipPath.concat("/{id}"), 134124)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
                .andExpect(jsonPath("$.errorCode", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("Spaceship not found with ID: 134124")));
    }

    @Test
    public void updateSpaceship_ShouldReturnUpdatedSpaceship_WhenRequestIsValid() throws Exception {
        SpaceshipDTO spaceship = new SpaceshipDTO(
                "Millennium Falcon",
                5,
                "34.75",
                "Hyperdrive",
                "A fast, modified freighter known for its speed and versatility."
        );

        this.mockMvc.perform(
                        put(spaceshipPath.concat("/{id}"), 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(spaceship)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo(spaceship.name())))
                .andExpect(jsonPath("$.crew_capacity", equalTo(spaceship.crewCapacity())))
                .andExpect(jsonPath("$.length", equalTo(spaceship.length())))
                .andExpect(jsonPath("$.propulsion_type", equalTo(spaceship.propulsionType())))
                .andExpect(jsonPath("$.description", equalTo(spaceship.description())));
    }

    @Test
    public void updateSpaceship_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        SpaceshipDTO spaceship = new SpaceshipDTO(
                "Millennium Falcon",
                5,
                "34.75",
                "Hyperdrive",
                "A fast, modified freighter known for its speed and versatility."
        );

        this.mockMvc.perform(
                        put(spaceshipPath.concat("/{id}"), 123123)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(spaceship)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
                .andExpect(jsonPath("$.errorCode", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("Spaceship not found with ID: 123123")));
    }

    @Test
    public void findAllSpaceships_ShouldReturnPageOfSpaceships_WhenRequestIsValid() throws Exception {
        this.mockMvc.perform(
                        get(spaceshipPath)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].name", equalTo("Millennium Falcon")))
                .andExpect(jsonPath("$.content[0].crew_capacity", equalTo(5)))
                .andExpect(jsonPath("$.content[0].propulsion_type", equalTo("Hyperdrive")))
                .andExpect(jsonPath("$.content[0].description", equalTo("A fast, modified freighter known for its speed and versatility.")));
    }

    @Test
    public void findAllSpaceships_ShouldReturnEmptyPage_WhenNoSpaceshipsExist() throws Exception {
        this.mockMvc.perform(
                        get(spaceshipPath)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].name", equalTo("Millennium Falcon")))
                .andExpect(jsonPath("$.content[0].crew_capacity", equalTo(5)))
                .andExpect(jsonPath("$.content[0].propulsion_type", equalTo("Hyperdrive")))
                .andExpect(jsonPath("$.content[0].description", equalTo("A fast, modified freighter known for its speed and versatility.")));
    }

    @Test
    public void findAllSpaceships_ShouldFilterResults_WhenFiltersAreProvided() throws Exception {
        this.mockMvc.perform(
                        get(spaceshipPath)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].name", equalTo("Millennium Falcon")))
                .andExpect(jsonPath("$.content[0].crew_capacity", equalTo(5)))
                .andExpect(jsonPath("$.content[0].propulsion_type", equalTo("Hyperdrive")))
                .andExpect(jsonPath("$.content[0].description", equalTo("A fast, modified freighter known for its speed and versatility.")));
    }

    @Test
    public void deleteSpaceship_ShouldReturnNoContent_WhenIdExists() throws Exception {
        this.mockMvc.perform(
                        delete(spaceshipPath.concat("/{id}"), 1)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteSpaceship_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        this.mockMvc.perform(
                        delete(spaceshipPath.concat("/{id}"), 123123)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
                .andExpect(jsonPath("$.errorCode", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("Spaceship not found with ID: 123123")));
    }

}
