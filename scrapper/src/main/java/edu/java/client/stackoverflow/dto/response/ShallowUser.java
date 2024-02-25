package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record ShallowUser(
    @JsonProperty("accept_rate")
    int acceptRate,

    @JsonProperty("account_id")
    int accountId,

    @JsonProperty("badge_counts")
    BadgeCount badgeCounts,

    @JsonProperty("display_name")
    String displayName,

    String link,

    @JsonProperty("profile_image")
    String profileImage,

    int reputation,

    @JsonProperty("user_id")
    int userId,

    @JsonProperty("user_type")
    String userType
) {
}
