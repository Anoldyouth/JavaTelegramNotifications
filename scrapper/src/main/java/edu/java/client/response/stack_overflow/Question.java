package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Question {
    @JsonProperty("accepted_answer_id")
    private int acceptedAnswerId;

    @JsonProperty("answer_count")
    private int answerCount;

    private Answer[] answers;

    private String body;

    @JsonProperty("body_markdown")
    private String bodyMarkdown;

    @JsonProperty("bounty_amount")
    private int bountyAmount;

    @JsonProperty("bounty_closes_date")
    private String bountyClosesDate;

    @JsonProperty("bounty_user")
    private ShallowUser bountyUser;

    @JsonProperty("can_answer")
    private boolean canAnswer;

    @JsonProperty("can_close")
    private boolean canClose;

    @JsonProperty("can_comment")
    private boolean canComment;

    @JsonProperty("can_edit")
    private boolean canEdit;

    @JsonProperty("can_flag")
    private boolean canFlag;

    @JsonProperty("can_suggest_edit")
    private boolean canSuggestEdit;

    @JsonProperty("close_vote_count")
    private int closeVoteCount;

    @JsonProperty("closed_date")
    private String closedDate;

    @JsonProperty("closed_details")
    private ClosedDetails closedDetails;

    @JsonProperty("closed_reason")
    private String closedReason;

    private Collective[] collectives;

    @JsonProperty("comment_count")
    private int commentCount;

    private Comment[] comments;

    @JsonProperty("community_owned_date")
    private String communityOwnedDate;

    @JsonProperty("content_license")
    private String contentLicense;

    @JsonProperty("creation_date")
    private String creationDate;

    @JsonProperty("delete_vote_count")
    private int deleteVoteCount;

    @JsonProperty("down_vote_count")
    private int downVoteCount;

    private boolean downvoted;

    @JsonProperty("favorite_count")
    private int favoriteCount;

    private boolean favorited;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    @JsonProperty("last_activity_date")
    private String lastActivityDate;

    @JsonProperty("last_edit_date")
    private String lastEditDate;

    @JsonProperty("last_editor")
    private ShallowUser lastEditor;

    private String link;

    @JsonProperty("locked_date")
    private String lockedDate;

    @JsonProperty("migrated_from")
    private MigrationInfo migratedFrom;

    @JsonProperty("migrated_to")
    private MigrationInfo migratedTo;

    private Notice notice;

    private ShallowUser owner;

    @JsonProperty("posted_by_collectives")
    private Collective[] postedByCollectives;

    @JsonProperty("protected_date")
    private String protectedDate;

    @JsonProperty("question_id")
    private int questionId;

    @JsonProperty("reopen_vote_count")
    private int reopenVoteCount;

    private int score;

    @JsonProperty("share_link")
    private String shareLink;

    private String[] tags;

    private String title;

    @JsonProperty("up_vote_count")
    private int upVoteCount;

    private boolean upvoted;

    private int viewCount;
}
