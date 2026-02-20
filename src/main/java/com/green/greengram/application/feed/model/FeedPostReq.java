package com.green.greengram.application.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class FeedPostReq {
    private String location;
    private String contents;
    private long signedUserId;
    private long feedId;
}
