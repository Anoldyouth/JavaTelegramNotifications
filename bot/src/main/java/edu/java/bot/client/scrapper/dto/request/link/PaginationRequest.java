package edu.java.bot.client.scrapper.dto.request.link;

public record PaginationRequest(
        String type,
        Long offset,
        String cursor,
        int limit
) {
}
