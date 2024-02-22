package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    private String url;

    @JsonProperty("repository_url")
    private String repositoryUrl;

    @JsonProperty("labels_url")
    private String labelsUrl;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    private int number;

    private String state;

    @JsonProperty("state_reason")
    private String stateReason;

    private String title;

    private String body;

    @JsonProperty("body_text")
    private String bodyText;

    @JsonProperty("body_html")
    private String bodyHtml;

    private SimpleUser user;

    private SimpleUser assignee;

    private SimpleUser[] assignees;

    private Milestone milestone;

    private boolean locked;

    @JsonProperty("active_lock_reason")
    private String activeLockReason;

    private int comments;

    @JsonProperty("pull_request")
    private PullRequest pullRequest;

    @JsonProperty("closed_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime closedAt;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime updatedAt;

    private boolean draft;

    @JsonProperty("closed_by")
    private SimpleUser closedBy;

    @JsonProperty("timeline_url")
    private String timelineUrl;

    private Repository repository;

    @JsonProperty("author_association")
    private String authorAssociation;

    @JsonProperty("performed_via_github_app")
    private GitHubApp performedViaGithubApp;

    private ReactionRollup reactions;

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
