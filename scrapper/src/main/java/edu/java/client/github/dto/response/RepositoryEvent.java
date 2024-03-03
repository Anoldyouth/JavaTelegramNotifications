package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public record RepositoryEvent(
    String id,

    String type,

    Actor actor,

    Repository repo,

    Actor org,

    Payload payload,

    @JsonProperty("public")
    boolean isPublic,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime createdAt
) {
}
