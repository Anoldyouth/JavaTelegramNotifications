package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record GitHubApp(
        long id,

        String slug,

        @JsonProperty("node_id")
        String nodeId,

        SimpleUser owner,

        String name,

        String description,

        @JsonProperty("external_url")
        String externalUrl,

        @JsonProperty("html_url")
        String htmlUrl,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime updatedAt,

        String[] events,

        @JsonProperty("installations_count")
        int installationsCount,

        @JsonProperty("client_id")
        String clientId,

        @JsonProperty("client_secret")
        String clientSecret,

        @JsonProperty("webhook_secret")
        String webhookSecret,

        String pem
) {
}
