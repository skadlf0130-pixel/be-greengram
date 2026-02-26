package com.green.greengram.application.feedcomment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class FeedCommentDeleteReq {
    private long feedCommentId;
    private long signedUserID;

}
