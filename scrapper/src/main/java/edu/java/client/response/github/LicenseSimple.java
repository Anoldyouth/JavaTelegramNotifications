package edu.java.client.response.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LicenseSimple {
    private String key;

    private String name;

    private String url;

    @JsonProperty("spdx_id")
    private String spdxId;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("html_url")
    private String htmlUrl;
}
