package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record Repository(
    long id,

    @JsonProperty("node_id")
    String nodeId,

    String name,

    @JsonProperty("full_name")
    String fullName,

    LicenseSimple license,

    int forks,

    SimpleUser owner,

    @JsonProperty("private")
    boolean isPrivate,

    @JsonProperty("html_url")
    String htmlUrl,

    String description,

    boolean fork,

    String url,

    @JsonProperty("archive_url")
    String archiveUrl,

    @JsonProperty("assignees_url")
    String assigneesUrl,

    @JsonProperty("blobs_url")
    String blobsUrl,

    @JsonProperty("branches_url")
    String branchesUrl,

    @JsonProperty("collaborators_url")
    String collaboratorsUrl,

    @JsonProperty("comments_url")
    String commentsUrl,

    @JsonProperty("commits_url")
    String commitsUrl,

    @JsonProperty("compare_url")
    String compareUrl,

    @JsonProperty("contents_url")
    String contentsUrl,

    @JsonProperty("contributors_url")
    String contributorsUrl,

    @JsonProperty("deployments_url")
    String deploymentsUrl,

    @JsonProperty("downloads_url")
    String downloadsUrl,

    @JsonProperty("events_url")
    String eventsUrl,

    @JsonProperty("forks_url")
    String forksUrl,

    @JsonProperty("git_commits_url")
    String gitCommitsUrl,

    @JsonProperty("git_refs_url")
    String gitRefsUrl,

    @JsonProperty("git_tags_url")
    String gitTagsUrl,

    @JsonProperty("git_url")
    String gitUrl,

    @JsonProperty("issue_comment_url")
    String issueCommentUrl,

    @JsonProperty("issue_events_url")
    String issueEventsUrl,

    @JsonProperty("issues_url")
    String issuesUrl,

    @JsonProperty("keys_url")
    String keysUrl,

    @JsonProperty("labels_url")
    String labelsUrl,

    @JsonProperty("languages_url")
    String languagesUrl,

    @JsonProperty("merges_url")
    String mergesUrl,

    @JsonProperty("milestones_url")
    String milestonesUrl,

    @JsonProperty("notifications_url")
    String notificationsUrl,

    @JsonProperty("pulls_url")
    String pullsUrl,

    @JsonProperty("releases_url")
    String releasesUrl,

    @JsonProperty("ssh_url")
    String sshUrl,

    @JsonProperty("stargazers_url")
    String stargazersUrl,

    @JsonProperty("statuses_url")
    String statusesUrl,

    @JsonProperty("subscribers_url")
    String subscribersUrl,

    @JsonProperty("subscription_url")
    String subscriptionUrl,

    @JsonProperty("tags_url")
    String tagsUrl,

    @JsonProperty("teams_url")
    String teamsUrl,

    @JsonProperty("trees_url")
    String treesUrl,

    @JsonProperty("clone_url")
    String cloneUrl,

    @JsonProperty("mirror_url")
    String mirrorUrl,

    @JsonProperty("hooks_url")
    String hooksUrl,

    @JsonProperty("svn_url")
    String svnUrl,

    String homepage,

    String language,

    @JsonProperty("forks_count")
    int forksCount,

    @JsonProperty("stargazers_count")
    int stargazersCount,

    @JsonProperty("watchers_count")
    int watchersCount,

    int size,

    @JsonProperty("default_branch")
    String defaultBranch,

    @JsonProperty("open_issues_count")
    int openIssuesCount,

    @JsonProperty("is_template")
    boolean isTemplate,

    String[] topics,

    @JsonProperty("has_issues")
    boolean hasIssues,

    @JsonProperty("has_projects")
    boolean hasProjects,

    @JsonProperty("has_wiki")
    boolean hasWiki,

    @JsonProperty("has_pages")
    boolean hasPages,

    @JsonProperty("has_discussions")
    boolean hasDiscussions,

    boolean archived,

    boolean disabled,

    String visibility,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime updatedAt,

    @JsonProperty("pushed_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime pushedAt,

    @JsonProperty("allow_rebase_merge")
    boolean allowRebaseMerge,

    @JsonProperty("temp_clone_token")
    String tempCloneToken,

    @JsonProperty("allow_squash_merge")
    boolean allowSquashMerge,

    @JsonProperty("allow_auto_merge")
    boolean allowAutoMerge,

    @JsonProperty("delete_branch_on_merge")
    boolean deleteBranchOnMerge,

    @JsonProperty("allow_update_branch")
    boolean allowUpdateBranch,

    @JsonProperty("squash_merge_commit_title")
    String squashMergeCommitTitle,

    @JsonProperty("squash_merge_commit_message")
    String squashMergeCommitMessage,

    @JsonProperty("merge_commit_title")
    String mergeCommitTitle,

    @JsonProperty("merge_commit_message")
    String mergeCommitMessage,

    @JsonProperty("allow_merge_commit")
    boolean allowMergeCommit,

    @JsonProperty("allow_forking")
    boolean allowForking,

    @JsonProperty("web_commit_signoff_required")
    boolean webCommitSignoffRequired,

    @JsonProperty("open_issues")
    int openIssues,

    int watchers,

    @JsonProperty("master_branch")
    String masterBranch,

    @JsonProperty("starred_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime starredAt,

    @JsonProperty("anonymous_access_enabled")
    boolean anonymousAccessEnabled
) {
}
