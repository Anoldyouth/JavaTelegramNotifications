package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record Actor(
    int id,

    String login,

    @JsonProperty("display_login")
    String displayLogin,

    @JsonProperty("gravatar_id")
    String gravatarId,

    String url,

    @JsonProperty("avatar_url")
    String avatarUrl
) {
}
