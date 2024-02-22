package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Page {
    @JsonProperty("page_name")
    private String pageName;

    private String title;

    private String summary;

    private String action;

    private String sha;

    @JsonProperty("html_url")
    private String htmlUrl;
}
