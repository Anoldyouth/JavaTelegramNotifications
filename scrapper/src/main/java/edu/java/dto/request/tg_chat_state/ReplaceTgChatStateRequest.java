package edu.java.dto.request.tg_chat_state;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record ReplaceTgChatStateRequest(
        @Schema(description = "Состояние чата", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive
        short state
) {
}
