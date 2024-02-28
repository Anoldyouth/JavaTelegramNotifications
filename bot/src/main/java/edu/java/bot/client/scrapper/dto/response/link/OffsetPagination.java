package edu.java.bot.client.scrapper.dto.response.link;

public record OffsetPagination(
        String type,
        long offset,
        int total,
        int limit
) implements Pagination {
}
