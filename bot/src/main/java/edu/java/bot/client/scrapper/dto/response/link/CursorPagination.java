package edu.java.bot.client.scrapper.dto.response.link;

import io.swagger.v3.oas.annotations.media.Schema;

public record CursorPagination(
        String type,
        String cursor,
        String previousCursor,

        String nextCursor,
        int limit
) implements Pagination {
}
