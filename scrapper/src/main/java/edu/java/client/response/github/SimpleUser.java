package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUser {
    private int id;

    private String login;

    private String name;

    private String email;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("display_login")
    private String displayLogin;

    @JsonProperty("gravatar_id")
    private String gravatarId;

    private String url;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("followers_url")
    private String followersUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("gists_url")
    private String gistsUrl;

    @JsonProperty("starred_url")
    private String starredUrl;

    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

    @JsonProperty("repos_url")
    private String reposUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("received_events_url")
    private String receivedEventsUrl;

    @JsonProperty("type")
    private String type;

    @JsonProperty("site_admin")
    private boolean siteAdmin;

    @JsonProperty("starred_at")
    @Setter(AccessLevel.NONE)
    OffsetDateTime starredAt;

    public void setStarredAt(String starredAt) {
        if (starredAt == null) {
            this.starredAt = null;

            return;
        }

        this.starredAt = OffsetDateTime.parse(starredAt);
    }
}
