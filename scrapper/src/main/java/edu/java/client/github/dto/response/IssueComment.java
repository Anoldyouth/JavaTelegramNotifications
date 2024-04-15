package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record IssueComment(
        long id,

        @JsonProperty("node_id")

        String nodeId,

        String url,

        String body,

        @JsonProperty("body_text")
        String bodyText,

        @JsonProperty("body_html")
        String bodyHtml,

        @JsonProperty("html_url")
        String htmlUrl,

        SimpleUser user,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime updatedAt,

        @JsonProperty("issue_url")
        String issueUrl,

        @JsonProperty("author_association")
        String authorAssociation,

        @JsonProperty("performed_via_github_app")
        GitHubApp performedViaGithubApp,

        ReactionRollup reactions
) {
}
