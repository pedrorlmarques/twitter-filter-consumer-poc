package com.pt.twitter.domain.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
class User {

    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("screen_name")
    private String screenName;

    private String location;

    private String url;

    private String description;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("profile_image_url_https")
    private String profileImageUrlHttps;

    @JsonProperty("default_profile_image")
    private boolean defaultProfileImage;

}
