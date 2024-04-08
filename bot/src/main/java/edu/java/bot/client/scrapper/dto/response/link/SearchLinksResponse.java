package edu.java.bot.client.scrapper.dto.response.link;

import java.util.List;

public record SearchLinksResponse(
        List<LinkResponse> links,
        OffsetPagination pagination
) {
}
