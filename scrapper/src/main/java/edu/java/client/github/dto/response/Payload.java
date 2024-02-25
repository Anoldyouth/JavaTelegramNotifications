package edu.java.client.github.dto.response;

import lombok.Generated;

@Generated
public record Payload(
    String action,

    Issue issue,

    IssueComment comment,

    Page[] pages
) {
}
