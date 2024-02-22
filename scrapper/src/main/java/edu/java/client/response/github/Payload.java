package edu.java.client.response.github;

import lombok.Data;

@Data
public class Payload {
    private String action;

    private Issue issue;

    private IssueComment comment;

    private Page[] pages;
}
