package edu.java.bot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record SendUpdateRequest(
        @Schema(nullable = true, example = "https://api.github.com")
        String url,

        @Schema(nullable = true, example = "Описание")
        String description,

        @Schema(description = "Массив идентификаторов телеграм чатов", example = "[1, 2, 3]")
        @NotNull
        List<@Positive Long> tgChatIds
) {
}
