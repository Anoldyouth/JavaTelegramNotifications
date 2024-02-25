package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record Question(
    @JsonProperty("accepted_answer_id")
    int acceptedAnswerId,

    @JsonProperty("answer_count")
    int answerCount,

    Answer[] answers,

    String body,

    @JsonProperty("body_markdown")
    String bodyMarkdown,

    @JsonProperty("bounty_amount")
    int bountyAmount,

    @JsonProperty("bounty_closes_date")
    String bountyClosesDate,

    @JsonProperty("bounty_user")
    ShallowUser bountyUser,

    @JsonProperty("can_answer")
    boolean canAnswer,

    @JsonProperty("can_close")
    boolean canClose,

    @JsonProperty("can_comment")
    boolean canComment,

    @JsonProperty("can_edit")
    boolean canEdit,

    @JsonProperty("can_flag")
    boolean canFlag,

    @JsonProperty("can_suggest_edit")
    boolean canSuggestEdit,

    @JsonProperty("close_vote_count")
    int closeVoteCount,

    @JsonProperty("closed_date")
    String closedDate,

    @JsonProperty("closed_details")
    ClosedDetails closedDetails,

    @JsonProperty("closed_reason")
    String closedReason,

    Collective[] collectives,

    @JsonProperty("comment_count")
    int commentCount,

    Comment[] comments,

    @JsonProperty("community_owned_date")
    String communityOwnedDate,

    @JsonProperty("content_license")
    String contentLicense,

    @JsonProperty("creation_date")
    String creationDate,

    @JsonProperty("delete_vote_count")
    int deleteVoteCount,

    @JsonProperty("down_vote_count")
    int downVoteCount,

    boolean downvoted,

    @JsonProperty("favorite_count")
    int favoriteCount,

    boolean favorited,

    @JsonProperty("is_answered")
    boolean isAnswered,

    @JsonProperty("last_activity_date")
    String lastActivityDate,

    @JsonProperty("last_edit_date")
    String lastEditDate,

    @JsonProperty("last_editor")
    ShallowUser lastEditor,

    String link,

    @JsonProperty("locked_date")
    String lockedDate,

    @JsonProperty("migrated_from")
    MigrationInfo migratedFrom,

    @JsonProperty("migrated_to")
    MigrationInfo migratedTo,

    Notice notice,

    ShallowUser owner,

    @JsonProperty("posted_by_collectives")
    Collective[] postedByCollectives,

    @JsonProperty("protected_date")
    String protectedDate,

    @JsonProperty("question_id")
    int questionId,

    @JsonProperty("reopen_vote_count")
    int reopenVoteCount,

    int score,

    @JsonProperty("share_link")
    String shareLink,

    String[] tags,

    String title,

    @JsonProperty("up_vote_count")
    int upVoteCount,

    boolean upvoted,

    int viewCount
) {
}
