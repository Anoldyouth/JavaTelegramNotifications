package edu.java.bot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class TooManyRequestsResponse {
        @Schema(description = "Описание ошибки", example = "Too Many Requests")
        @NotNull
        public final String description = HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase();

        @Schema(description = "Код ошибки", example = "429")
        public final int code = HttpStatus.TOO_MANY_REQUESTS.value();
}
