package edu.java.bot.client.scrapper.dto.response.link;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursorPagination extends Pagination {
    private String cursor;
    private String previousCursor;
    private String nextCursor;
    private int limit;
}
