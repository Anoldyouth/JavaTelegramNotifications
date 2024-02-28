package edu.java.bot.client.scrapper.dto.request.link;

public record SearchLinksRequest(
        Filter filter,
        PaginationRequest pagination
) {
}
