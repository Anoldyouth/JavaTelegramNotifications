package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record PullRequest(
    @JsonProperty("merged_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime mergedAt,

    @JsonProperty("diff_url")
    String diffUrl,

    @JsonProperty("html_url")
    String htmlUrl,

    @JsonProperty("patch_url")
    String patchUrl,

    String url
) {
}
