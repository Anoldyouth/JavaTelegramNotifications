package edu.java.dto.response.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ValidationError(
        @Schema(description = "Поле, в котором допущена ошибка", example = "name")
        @NotNull
        String field,
        @Schema(description = "Описание ошибки", example = "Поле не должно быть пустым")
        @NotNull
        String message
) {
}
