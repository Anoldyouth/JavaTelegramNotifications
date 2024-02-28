package edu.java.bot.client.scrapper.dto.request.link;

import java.net.URI;

public record CreateLinkRequest(
        long tgChatId,

        URI link
) {
}
