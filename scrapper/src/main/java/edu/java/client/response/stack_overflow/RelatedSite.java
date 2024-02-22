package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RelatedSite {
    @JsonProperty("api_site_parameter")
    private String apiSiteParameter;

    private String name;

    private String relation;

    @JsonProperty("site_url")
    private String siteUrl;
}
