package edu.java.bot.client.scrapper.dto.request.link;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record Filter(
        long tgChatId
) {
}
