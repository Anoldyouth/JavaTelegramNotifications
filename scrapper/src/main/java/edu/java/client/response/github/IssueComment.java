package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueComment {
    private int id;

    @JsonProperty("node_id")

    private String nodeId;

    private String url;

    private String body;

    @JsonProperty("body_text")
    private String bodyText;

    @JsonProperty("body_html")
    private String bodyHtml;

    @JsonProperty("html_url")
    private String htmlUrl;

    private SimpleUser user;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime updatedAt;

    @JsonProperty("issue_url")
    private String issueUrl;

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
}
