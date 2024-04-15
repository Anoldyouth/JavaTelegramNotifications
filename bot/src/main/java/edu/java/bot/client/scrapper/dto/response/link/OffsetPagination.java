package edu.java.bot.client.scrapper.dto.response.link;

public record OffsetPagination(
        long offset,
        long total,
        long limit
) {
}
