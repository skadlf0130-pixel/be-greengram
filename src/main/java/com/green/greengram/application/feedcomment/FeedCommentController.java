package com.green.greengram.application.feedcomment;

import com.green.greengram.application.feedcomment.model.FeedCommentDeleteReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetReq;
import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.application.feedcomment.model.FeedCommentPostReq;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService feedCommentService;

    @PostMapping
    public ResultResponse<?> postFeedComment(@AuthenticationPrincipal UserPrincipal userPrincipal
            , @RequestBody FeedCommentPostReq req) {

        req.setSignedUserId( userPrincipal.getSignedUserId() );
        log.info("req: {}", req);
        long feedCommentId = feedCommentService.postFeedComment( req );
        return new ResultResponse<>("댓글 등록 완료", feedCommentId);
    }
    @GetMapping
    public ResultResponse<?> getFeedCommentList(@ModelAttribute FeedCommentGetReq req) {
        log.info("req: {}", req);
        List<FeedCommentGetRes> list = feedCommentService.getFeedCommentList(req);
        return new ResultResponse<>(String.format("%d rows", list.size()), list);
    }

    @DeleteMapping
    public ResultResponse<?> deleteFeedComment(@AuthenticationPrincipal UserPrincipal userPrincipal
                                              , @ModelAttribute FeedCommentDeleteReq req){
        req.setSignedUserID(userPrincipal.getSignedUserId());
        int result = feedCommentService.deleteFeedComment(req);
        return new ResultResponse<>("댓글 삭제 완료", result);
    }
}
/*데이터 보내는 방식
get/delete 유사 : QueryString or pathVariable
post/put/patch 유사 : JSON, 파일업로드 때만 FormData

로그인 정보가 필요한 경우
 - 글 등록, 수정, 삭제

로그인 안 한 사용자가 이용 할 수 있나? 없나?가 중요
 - 읽기(단, 읽기라도 경우에 따라 로그인 정보가 필요할 수 있음, Feed를 좋아요 했는지 안 했는지 알아야 할 때)
*/