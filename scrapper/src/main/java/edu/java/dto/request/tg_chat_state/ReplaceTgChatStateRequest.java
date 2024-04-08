package edu.java.dto.request.tg_chat_state;

import edu.java.util.State;
import io.swagger.v3.oas.annotations.media.Schema;

public record ReplaceTgChatStateRequest(
        @Schema(description = "Состояние чата", example = "MAIN", requiredMode = Schema.RequiredMode.REQUIRED)
        State state
) {
}
