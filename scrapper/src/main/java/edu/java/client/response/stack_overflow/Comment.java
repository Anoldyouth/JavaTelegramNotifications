package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Comment {
    private String body;

    @JsonProperty("body_markdown")
    private String bodyMarkdown;

    @JsonProperty("can_flag")
    private boolean canFlag;

    @JsonProperty("comment_id")
    private int commentId;

    @JsonProperty("content_license")
    private String contentLicense;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    private boolean edited;

    private String link;

    private ShallowUser owner;

    @JsonProperty("post_id")
    private int postId;

    @JsonProperty("post_type")
    private String postType;

    @JsonProperty("reply_to_user")
    private ShallowUser replyToUser;

    private int score;

    private boolean upvoted;
}
