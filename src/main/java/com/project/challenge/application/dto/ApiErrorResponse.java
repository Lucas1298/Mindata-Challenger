package com.project.challenge.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

@Schema(name = "ApiErrorResponse", description = "Objeto de respuesta para errores de API")
public record ApiErrorResponse(
        @Schema(description = "Estado HTTP del error", example = "BAD_REQUEST")
        HttpStatus status,

        @Schema(description = "Código de error interno", example = "4001")
        Integer errorCode,

        @Schema(description = "Mensaje descriptivo del error", example = "El parámetro X es inválido")
        String message
) {
}
