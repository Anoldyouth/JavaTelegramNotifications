package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record SimpleUser(
    int id,

    String login,

    String name,

    String email,

    @JsonProperty("node_id")
    String nodeId,

    @JsonProperty("display_login")
    String displayLogin,

    @JsonProperty("gravatar_id")
    String gravatarId,

    String url,

    @JsonProperty("avatar_url")
    String avatarUrl,

    @JsonProperty("html_url")
    String htmlUrl,

    @JsonProperty("followers_url")
    String followersUrl,

    @JsonProperty("following_url")
    String followingUrl,

    @JsonProperty("gists_url")
    String gistsUrl,

    @JsonProperty("starred_url")
    String starredUrl,

    @JsonProperty("subscriptions_url")
    String subscriptionsUrl,

    @JsonProperty("organizations_url")
    String organizationsUrl,

    @JsonProperty("repos_url")
    String reposUrl,

    @JsonProperty("events_url")
    String eventsUrl,

    @JsonProperty("received_events_url")
    String receivedEventsUrl,

    @JsonProperty("type")
    String type,

    @JsonProperty("site_admin")
    boolean siteAdmin,

    @JsonProperty("starred_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime starredAt
) {
}
