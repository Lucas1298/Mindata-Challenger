package com.project.challenge.infrastructure.rest.controller.spaceship;

import com.project.challenge.application.dto.SpaceshipDTO;
import com.project.challenge.application.mapper.SpaceshipMapper;
import com.project.challenge.domain.port.spaceship.SpaceshipService;
import com.project.challenge.infrastructure.rest.controller.spaceship.dto.SpaceshipResponseDTO;
import com.project.challenge.application.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(path = "/v1.0/spaceship")
@Tag(name = "Spaceship")
@RestController
public class SpaceshipController {

    @Autowired
    private SpaceshipService spaceshipService;

    @Autowired
    private SpaceshipMapper spaceshipMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nave espacial creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<SpaceshipResponseDTO> create(@RequestBody SpaceshipDTO req){
        return ResponseEntity.ok(spaceshipService.save(req));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nave espacial encontrada"),
            @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<SpaceshipResponseDTO> find(@PathVariable Integer id) {
        return ResponseEntity.ok(spaceshipService.findSpaceship(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public void delete(@PathVariable Integer id)  {
        spaceshipService.delete(id);
    }

    @PutMapping(value = "/{id}",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nave espacial actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Nave espacial no encontrada", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public ResponseEntity<SpaceshipResponseDTO> update(@RequestBody SpaceshipDTO req, @PathVariable Integer id){
        return ResponseEntity.ok(spaceshipService.update(id, req));
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de naves espaciales obtenido correctamente")
    })
    public ResponseEntity<Page<SpaceshipResponseDTO>> findAll(
            Pageable pageable,
            @Parameter(description = "Filters for the query",
                    example = "name=Falcon;description=fast",
                    required = false)
            @RequestParam(value = "filters", required = false) String filters) {
        return  ResponseEntity.ok(spaceshipService.findAll(pageable,filters));
    }
}