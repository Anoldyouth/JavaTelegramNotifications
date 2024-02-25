package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record Comment(
    String body,

    @JsonProperty("body_markdown")
    String bodyMarkdown,

    @JsonProperty("can_flag")
    boolean canFlag,

    @JsonProperty("comment_id")
    int commentId,

    @JsonProperty("content_license")
    String contentLicense,

    @JsonProperty("creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime creationDate,

    boolean edited,

    String link,

    ShallowUser owner,

    @JsonProperty("post_id")
    int postId,

    @JsonProperty("post_type")
    String postType,

    @JsonProperty("reply_to_user")
    ShallowUser replyToUser,

    int score,

    boolean upvoted
) {
}
