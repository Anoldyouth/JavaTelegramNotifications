package edu.java.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Generated;

@Generated
public record Site(
    String[] aliases,

    @JsonProperty("api_site_parameter")
    String apiSiteParameter,

    String audience,

    @JsonProperty("closed_beta_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime closedBetaDate,

    @JsonProperty("favicon_url")
    String faviconUrl,

    @JsonProperty("high_resolution_icon_url")
    String highResolutionIconUrl,

    @JsonProperty("icon_url")
    String iconUrl,

    @JsonProperty("launch_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime launchDate,

    @JsonProperty("logo_url")
    String logoUrl,

    @JsonProperty("markdown_extensions")
    String[] markdownExtensions,

    String name,

    @JsonProperty("open_beta_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    OffsetDateTime openBetaDate,

    @JsonProperty("related_sites")
    RelatedSite[] relatedSites,

    @JsonProperty("site_state")
    String siteState,

    @JsonProperty("site_type")
    String siteType,

    @JsonProperty("site_url")
    String siteUrl,

    Styling styling,

    @JsonProperty("twitter_account")
    String twitterAccount
) {
}
