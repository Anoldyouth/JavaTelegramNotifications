package edu.java.dto.request.link;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;

public record CreateLinkRequest(
        @Schema(
                description = "Ссылка для отслеживания",
                example = "https://api.github.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        URI link
) {
}
