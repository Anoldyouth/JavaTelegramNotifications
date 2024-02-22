package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class PullRequest {
    @JsonProperty("merged_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime mergedAt;

    @JsonProperty("diff_url")
    private String diffUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("patch_url")
    private String patchUrl;

    private String url;

    public void setMergedAt(String mergedAt) {
        if (mergedAt == null) {
            this.mergedAt = null;

            return;
        }

        this.mergedAt = OffsetDateTime.parse(mergedAt);
    }
}
