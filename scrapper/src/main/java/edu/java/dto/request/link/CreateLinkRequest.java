package edu.java.dto.request.link;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

public record CreateLinkRequest(
        @Schema(description = "Идентификатор чата", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        long tgChatId,

        @Schema(
                description = "Ссылка для отслеживания",
                example = "https://api.github.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @URL
        String link
) {
}
