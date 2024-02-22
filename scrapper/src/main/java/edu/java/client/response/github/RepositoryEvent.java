package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryEvent {
    private String id;

    private String type;

    private Actor actor;

    private Repository repo;

    private Actor org;

    private Payload payload;

    @JsonProperty("public")
    private boolean isPublic;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)

    private OffsetDateTime createdAt;

    public void setCreatedAt(String createdAt) {
        this.createdAt = OffsetDateTime.parse(createdAt);
    }
}
