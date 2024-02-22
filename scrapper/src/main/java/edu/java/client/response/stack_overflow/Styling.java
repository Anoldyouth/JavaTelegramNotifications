package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Styling {
    @JsonProperty("link_color")
    private String linkColor;

    @JsonProperty("tag_background_color")
    private String tagBackgroundColor;

    @JsonProperty("tag_foreground_color")
    private String tagForegroundColor;
}
