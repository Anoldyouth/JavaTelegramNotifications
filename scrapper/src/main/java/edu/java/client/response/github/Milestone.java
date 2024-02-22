package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Milestone {
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("labels_url")
    private String labelsUrl;

    private int number;

    private String state;

    private String title;

    private String description;

    private SimpleUser creator;

    @JsonProperty("open_issues")
    private int openIssues;

    @JsonProperty("closed_issues")
    private int closedIssues;

    @JsonProperty("closed_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime closedAt;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime updatedAt;

    @JsonProperty("due_on")
    private String dueOn;

    public void setCreatedAt(String createdAt) {
        this.createdAt = OffsetDateTime.parse(createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = OffsetDateTime.parse(updatedAt);
    }

    public void setClosedAt(String closedAt) {
        if (closedAt == null) {
            this.closedAt = null;

            return;
        }

        this.closedAt = OffsetDateTime.parse(closedAt);
    }
}
