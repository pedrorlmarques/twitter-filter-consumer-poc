package com.pt.twitter.domain.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetBody {

    private Long id;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("text")
    private String text;

    private String source;

    private boolean truncated;

    @JsonProperty("in_reply_to_status_id")
    private long inReplyToStatusId;

    @JsonProperty("in_reply_to_user_id")
    private long inReplyToUserId;

    @JsonProperty("in_reply_to_screen_name")
    private String inReplyToScreenName;

    @JsonProperty("user")
    private User user;

}
