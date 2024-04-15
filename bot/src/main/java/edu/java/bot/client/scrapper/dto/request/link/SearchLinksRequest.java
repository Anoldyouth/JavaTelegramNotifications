package edu.java.bot.client.scrapper.dto.request.link;

public record SearchLinksRequest(
        Long offset,
        Long limit
) {
}
