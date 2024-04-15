package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record Issue(
    long id,

    @JsonProperty("node_id")
    String nodeId,

    String url,

    @JsonProperty("repository_url")
    String repositoryUrl,

    @JsonProperty("labels_url")
    String labelsUrl,

    @JsonProperty("comments_url")
    String commentsUrl,

    @JsonProperty("events_url")
    String eventsUrl,

    @JsonProperty("html_url")
    String htmlUrl,

    int number,

    String state,

    @JsonProperty("state_reason")
    String stateReason,

    String title,

    String body,

    @JsonProperty("body_text")
    String bodyText,

    @JsonProperty("body_html")
    String bodyHtml,

    SimpleUser user,

    SimpleUser assignee,

    SimpleUser[] assignees,

    Milestone milestone,

    boolean locked,

    @JsonProperty("active_lock_reason")
    String activeLockReason,

    int comments,

    @JsonProperty("pull_request")
    PullRequest pullRequest,

    @JsonProperty("closed_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime closedAt,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime updatedAt,

    boolean draft,

    @JsonProperty("closed_by")
    SimpleUser closedBy,

    @JsonProperty("timeline_url")
    String timelineUrl,

    Repository repository,

    @JsonProperty("author_association")
    String authorAssociation,

    @JsonProperty("performed_via_github_app")
    GitHubApp performedViaGithubApp,

    ReactionRollup reactions
) {
}
