package edu.java.client.github.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record LicenseSimple(
    String key,

    String name,

    String url,

    @JsonProperty("spdx_id")
    String spdxId,

    @JsonProperty("node_id")
    String nodeId,

    @JsonProperty("html_url")
    String htmlUrl
) {
}
