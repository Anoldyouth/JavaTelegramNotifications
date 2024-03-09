package edu.java.bot.client.scrapper.dto.response.link;

import java.net.URI;

public record LinkResponse(
        long id,
        URI url
) {
}
