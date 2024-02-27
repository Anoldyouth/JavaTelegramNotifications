package edu.java.dto.request.link;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record Filter(
        @Schema(description = "Идентификатор чата", example = "1")
        @Positive
        long tgChatId
) {
}
