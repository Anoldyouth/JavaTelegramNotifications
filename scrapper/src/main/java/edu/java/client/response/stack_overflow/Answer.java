package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Answer {
    private boolean accepted;

    @JsonProperty("answer_id")
    private int answerId;

    @JsonProperty("awarded_bounty_amount")
    private int awardedBountyAmount;

    @JsonProperty("awarded_bounty_users")
    private ShallowUser[] awardedBountyUsers;

    private String body;

    @JsonProperty("body_markdown")
    private String bodyMarkdown;

    @JsonProperty("can_comment")
    private boolean canComment;

    @JsonProperty("can_edit")
    private boolean canEdit;

    @JsonProperty("can_flag")
    private boolean canFlag;

    @JsonProperty("can_suggest_edit")
    private boolean canSuggestEdit;

    private Collective[] collectives;

    @JsonProperty("comment_count")
    private int commentCount;

    private Comment[] comments;

    @JsonProperty("community_owned_date")
    private OffsetDateTime communityOwnedDate;

    @JsonProperty("content_license")
    private String contentLicense;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @JsonProperty("down_vote_count")
    private int downVoteCount;

    private boolean downvoted;

    @JsonProperty("is_accepted")
    private boolean isAccepted;

    @JsonProperty("last_activity_date")
    private OffsetDateTime lastActivityDate;

    @JsonProperty("last_edit_date")
    private OffsetDateTime lastEditDate;

    @JsonProperty("last_editor")
    private ShallowUser lastEditor;

    private String link;

    @JsonProperty("locked_date")
    private OffsetDateTime lockedDate;

    private ShallowUser owner;

    @JsonProperty("posted_by_collectives")
    private Collective[] postedByCollectives;

    @JsonProperty("question_id")
    private int questionId;

    private CollectiveRecommendation[] recommendations;

    private int score;

    @JsonProperty("share_link")
    private String shareLink;

    private String[] tags;

    private String title;

    @JsonProperty("up_vote_count")
    private int upVoteCount;

    private boolean upvoted;
}
