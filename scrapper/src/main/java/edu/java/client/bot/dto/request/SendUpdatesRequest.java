package edu.java.client.bot.dto.request;

import java.net.URI;
import java.util.List;

public record SendUpdatesRequest(
        long id,
        URI url,
        String description,
        List<Long> tgChatIds
) {
}
