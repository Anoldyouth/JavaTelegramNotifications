package edu.java.model;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record Link(
        long id,
        URI url,
        OffsetDateTime lastCheckAt,
        OffsetDateTime createdAt
) {
    public record FindAllResult(List<Link> links, long count) {
    }
}
