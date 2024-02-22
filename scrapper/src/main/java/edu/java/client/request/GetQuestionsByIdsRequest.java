package edu.java.client.request;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
@SuppressWarnings("MagicNumber")
public class GetQuestionsByIdsRequest {
    private int page = 1;

    private int pageSize = 100;

    private OffsetDateTime fromDate;

    private OffsetDateTime toDate;

    private String order;

    private OffsetDateTime min;

    private OffsetDateTime max;

    private String sort;

    private String filter;
}
