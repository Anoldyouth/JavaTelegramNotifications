package edu.java.bot.client.scrapper.dto.request.link;

public record PaginationRequest(
        String type,
        long offset,
        String cursor,
        int limit
) {
}
