package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShallowUser {
    @JsonProperty("accept_rate")
    private int acceptRate;

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("badge_counts")
    private BadgeCount badgeCounts;

    @JsonProperty("display_name")
    private String displayName;

    private String link;

    @JsonProperty("profile_image")
    private String profileImage;

    private int reputation;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("user_type")
    private String userType;
}
