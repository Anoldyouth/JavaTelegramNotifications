package edu.java.bot.client.scrapper.dto.response.link;

public record SearchLinksResponse(
        LinkResponse[] links,
        Pagination pagination
) {
}
