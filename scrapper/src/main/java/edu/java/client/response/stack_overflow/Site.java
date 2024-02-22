package edu.java.client.response.stack_overflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Site {
    private String[] aliases;

    @JsonProperty("api_site_parameter")
    private String apiSiteParameter;

    private String audience;

    @JsonProperty("closed_beta_date")
    private OffsetDateTime closedBetaDate;

    @JsonProperty("favicon_url")
    private String faviconUrl;

    @JsonProperty("high_resolution_icon_url")
    private String highResolutionIconUrl;

    @JsonProperty("icon_url")
    private String iconUrl;

    @JsonProperty("launch_date")
    private OffsetDateTime launchDate;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("markdown_extensions")
    private String[] markdownExtensions;

    private String name;

    @JsonProperty("open_beta_date")
    private OffsetDateTime openBetaDate;

    @JsonProperty("related_sites")
    private RelatedSite[] relatedSites;

    @JsonProperty("site_state")
    private String siteState;

    @JsonProperty("site_type")
    private String siteType;

    @JsonProperty("site_url")
    private String siteUrl;

    private Styling styling;

    @JsonProperty("twitter_account")
    private String twitterAccount;
}
