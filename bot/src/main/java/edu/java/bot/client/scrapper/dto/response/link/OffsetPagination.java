package edu.java.bot.client.scrapper.dto.response.link;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffsetPagination extends Pagination {
    private int offset;
    private int total;
    private int limit;
}
