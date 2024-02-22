package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class Repository {
    private int id;

    @JsonProperty("node_id")
    private String nodeId;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private LicenseSimple license;

    private int forks;

    private SimpleUser owner;

    @JsonProperty("private")
    private boolean isPrivate;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String description;

    private boolean fork;

    private String url;

    @JsonProperty("archive_url")
    private String archiveUrl;

    @JsonProperty("assignees_url")
    private String assigneesUrl;

    @JsonProperty("blobs_url")
    private String blobsUrl;

    @JsonProperty("branches_url")
    private String branchesUrl;

    @JsonProperty("collaborators_url")
    private String collaboratorsUrl;

    @JsonProperty("comments_url")
    private String commentsUrl;

    @JsonProperty("commits_url")
    private String commitsUrl;

    @JsonProperty("compare_url")
    private String compareUrl;

    @JsonProperty("contents_url")
    private String contentsUrl;

    @JsonProperty("contributors_url")
    private String contributorsUrl;

    @JsonProperty("deployments_url")
    private String deploymentsUrl;

    @JsonProperty("downloads_url")
    private String downloadsUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("forks_url")
    private String forksUrl;

    @JsonProperty("git_commits_url")
    private String gitCommitsUrl;

    @JsonProperty("git_refs_url")
    private String gitRefsUrl;

    @JsonProperty("git_tags_url")
    private String gitTagsUrl;

    @JsonProperty("git_url")
    private String gitUrl;

    @JsonProperty("issue_comment_url")
    private String issueCommentUrl;

    @JsonProperty("issue_events_url")
    private String issueEventsUrl;

    @JsonProperty("issues_url")
    private String issuesUrl;

    @JsonProperty("keys_url")
    private String keysUrl;

    @JsonProperty("labels_url")
    private String labelsUrl;

    @JsonProperty("languages_url")
    private String languagesUrl;

    @JsonProperty("merges_url")
    private String mergesUrl;

    @JsonProperty("milestones_url")
    private String milestonesUrl;

    @JsonProperty("notifications_url")
    private String notificationsUrl;

    @JsonProperty("pulls_url")
    private String pullsUrl;

    @JsonProperty("releases_url")
    private String releasesUrl;

    @JsonProperty("ssh_url")
    private String sshUrl;

    @JsonProperty("stargazers_url")
    private String stargazersUrl;

    @JsonProperty("statuses_url")
    private String statusesUrl;

    @JsonProperty("subscribers_url")
    private String subscribersUrl;

    @JsonProperty("subscription_url")
    private String subscriptionUrl;

    @JsonProperty("tags_url")
    private String tagsUrl;

    @JsonProperty("teams_url")
    private String teamsUrl;

    @JsonProperty("trees_url")
    private String treesUrl;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("mirror_url")
    private String mirrorUrl;

    @JsonProperty("hooks_url")
    private String hooksUrl;

    @JsonProperty("svn_url")
    private String svnUrl;

    private String homepage;

    private String language;

    @JsonProperty("forks_count")
    private int forksCount;

    @JsonProperty("stargazers_count")
    private int stargazersCount;

    @JsonProperty("watchers_count")
    private int watchersCount;

    private int size;

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("open_issues_count")
    private int openIssuesCount;

    @JsonProperty("is_template")
    private boolean isTemplate = false;

    private String[] topics;

    @JsonProperty("has_issues")
    private boolean hasIssues;

    @JsonProperty("has_projects")
    private boolean hasProjects;

    @JsonProperty("has_wiki")
    private boolean hasWiki;

    @JsonProperty("has_pages")
    private boolean hasPages;

    @JsonProperty("has_discussions")
    private boolean hasDiscussions = false;

    private boolean archived;

    private boolean disabled;

    private String visibility;

    @JsonProperty("created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime updatedAt;

    @JsonProperty("pushed_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime pushedAt;

    @JsonProperty("allow_rebase_merge")
    private boolean allowRebaseMerge = true;

    @JsonProperty("temp_clone_token")
    private String tempCloneToken;

    @JsonProperty("allow_squash_merge")
    private boolean allowSquashMerge = true;

    @JsonProperty("allow_auto_merge")
    private boolean allowAutoMerge = false;

    @JsonProperty("delete_branch_on_merge")
    private boolean deleteBranchOnMerge = false;

    @JsonProperty("allow_update_branch")
    private boolean allowUpdateBranch = false;

    @JsonProperty("squash_merge_commit_title")
    private String squashMergeCommitTitle;

    @JsonProperty("squash_merge_commit_message")
    private String squashMergeCommitMessage;

    @JsonProperty("merge_commit_title")
    private String mergeCommitTitle;

    @JsonProperty("merge_commit_message")
    private String mergeCommitMessage;

    @JsonProperty("allow_merge_commit")
    private boolean allowMergeCommit = true;

    @JsonProperty("allow_forking")
    private boolean allowForking;

    @JsonProperty("web_commit_signoff_required")
    private boolean webCommitSignoffRequired = false;

    @JsonProperty("open_issues")
    private int openIssues;

    private int watchers;

    @JsonProperty("master_branch")
    private String masterBranch;

    @JsonProperty("starred_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime starredAt;

    @JsonProperty("anonymous_access_enabled")
    private boolean anonymousAccessEnabled;

    public void setCreatedAt(String createdAt) {
        this.createdAt = OffsetDateTime.parse(createdAt);
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = OffsetDateTime.parse(updatedAt);
    }

    public void setPushedAt(String pushedAt) {
        if (pushedAt == null) {
            this.pushedAt = null;

            return;
        }

        this.pushedAt = OffsetDateTime.parse(pushedAt);
    }

    public void setStarredAt(String starredAt) {
        if (starredAt == null) {
            this.starredAt = null;

            return;
        }

        this.starredAt = OffsetDateTime.parse(starredAt);
    }
}
