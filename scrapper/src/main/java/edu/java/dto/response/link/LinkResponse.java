package edu.java.dto.response.link;

import io.swagger.v3.oas.annotations.media.Schema;

public record LinkResponse(
        @Schema(description = "Идентификатор ссылки", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @Schema(
                description = "Отслеживаемая ссылка",
                example = "https://api.github.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String url
) {
}
