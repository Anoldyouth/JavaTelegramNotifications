package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class GitHubApp {
    private int id;

    private String slug;

    @JsonProperty("node_id")
    private String nodeId;

    private SimpleUser owner;

    private String name;

    private String description;

    @JsonProperty("external_url")
    private String externalUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime updatedAt;

    private String[] events;

    @JsonProperty("installations_count")
    private int installationsCount;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("webhook_secret")
    private String webhookSecret;

    private String pem;

    public void setCreatedAt(String createdAt) {
        this.createdAt = OffsetDateTime.parse(createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = OffsetDateTime.parse(updatedAt);
    }
}
