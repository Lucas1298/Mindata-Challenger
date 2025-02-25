package com.project.challenge.infrastructure.rest.controller.spaceship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


public record SpaceshipResponseDTO(

    @JsonProperty("id")
    @Schema(description = "The id of the spaceship", example = "1")
    String id,

    @JsonProperty("name")
    @Schema(description = "The name of the spaceship", example = "Millennium Falcon")
    String name,

    @JsonProperty("crew_capacity")
    @Schema(description = "The maximum number of crew members the spaceship can carry", example = "5")
    Integer crewCapacity,

    @JsonProperty("length")
    @Schema(description = "The length of the spaceship in meters", example = "34.75")
    String length,

    @JsonProperty("propulsion_type")
    @Schema(description = "The type of propulsion used by the spaceship", example = "Hyperdrive")
    String propulsionType,

    @JsonProperty("description")
    @Schema(description = "A brief description of the spaceship", example = "A fast, modified freighter known for its speed and versatility.")
    String description

) implements Serializable {}


