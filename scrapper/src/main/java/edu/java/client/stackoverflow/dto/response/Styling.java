package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record Styling(
    @JsonProperty("link_color")
    String linkColor,

    @JsonProperty("tag_background_color")
    String tagBackgroundColor,

    @JsonProperty("tag_foreground_color")
    String tagForegroundColor
) {
}
