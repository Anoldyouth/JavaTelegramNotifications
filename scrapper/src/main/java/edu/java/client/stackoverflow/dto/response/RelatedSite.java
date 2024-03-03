package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;

@Generated
public record RelatedSite(
    @JsonProperty("api_site_parameter")
    String apiSiteParameter,

    String name,

    String relation,

    @JsonProperty("site_url")
    String siteUrl
) {
}
