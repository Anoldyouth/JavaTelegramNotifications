package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record Milestone(
        long id,

    @JsonProperty("node_id")
    String nodeId,

    String url,

    @JsonProperty("html_url")
    String htmlUrl,

    @JsonProperty("labels_url")
    String labelsUrl,

    int number,

    String state,

    String title,

    String description,

    SimpleUser creator,

    @JsonProperty("open_issues")
    int openIssues,

    @JsonProperty("closed_issues")
    int closedIssues,

    @JsonProperty("closed_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime closedAt,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime updatedAt,

    @JsonProperty("due_on")
    String dueOn
) {
}
