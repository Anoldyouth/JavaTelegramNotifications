package edu.java.dto.request.link;

public record SearchLinksRequest(
        Filter filter,
        PaginationRequest pagination
) {
}
