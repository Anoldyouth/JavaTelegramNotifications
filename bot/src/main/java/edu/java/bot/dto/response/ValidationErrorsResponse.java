package edu.java.bot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ValidationErrorsResponse(
        @Schema(description = "Описание ошибки", example = "Validation Error")
        @NotNull
        String description,
        @Schema(description = "Код ошибки", example = "400")
        @NotNull
        String code,
        @NotNull
        List<ValidationError> errors
) {
}
