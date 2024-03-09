package edu.java.dto.response.tg_chat_state;

import io.swagger.v3.oas.annotations.media.Schema;

public record TgChatStateResponse(
        @Schema(description = "Идентификатор чата", example = "1")
        long tgChatId,
        @Schema(description = "Состояние чата", example = "1")
        short state
) {
}
