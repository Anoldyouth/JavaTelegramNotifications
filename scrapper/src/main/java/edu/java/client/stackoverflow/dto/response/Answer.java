package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Generated;

@Generated
public record Answer(
    boolean accepted,

    @JsonProperty("answer_id")
    int answerId,

    @JsonProperty("awarded_bounty_amount")
    int awardedBountyAmount,

    @JsonProperty("awarded_bounty_users")
    List<ShallowUser> awardedBountyUsers,

    String body,

    @JsonProperty("body_markdown")
    String bodyMarkdown,

    @JsonProperty("can_comment")
    boolean canComment,

    @JsonProperty("can_edit")
    boolean canEdit,

    @JsonProperty("can_flag")
    boolean canFlag,

    @JsonProperty("can_suggest_edit")
    boolean canSuggestEdit,

    List<Collective> collectives,

    @JsonProperty("comment_count")
    int commentCount,

    List<Comment> comments,

    @JsonProperty("community_owned_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime communityOwnedDate,

    @JsonProperty("content_license")
    String contentLicense,

    @JsonProperty("creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime creationDate,

    @JsonProperty("down_vote_count")
    int downVoteCount,

    boolean downvoted,

    @JsonProperty("is_accepted")
    boolean isAccepted,

    @JsonProperty("last_activity_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime lastActivityDate,

    @JsonProperty("last_edit_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime lastEditDate,

    @JsonProperty("last_editor")
    ShallowUser lastEditor,

    String link,

    @JsonProperty("locked_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime lockedDate,

    ShallowUser owner,

    @JsonProperty("posted_by_collectives")
    List<Collective> postedByCollectives,

    @JsonProperty("question_id")
    int questionId,

    List<CollectiveRecommendation> recommendations,

    int score,

    @JsonProperty("share_link")
    String shareLink,

    List<String> tags,

    String title,

    @JsonProperty("up_vote_count")
    int upVoteCount,

    boolean upvoted
) {
}
