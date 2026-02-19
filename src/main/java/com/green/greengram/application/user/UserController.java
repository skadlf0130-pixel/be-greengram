package com.green.greengram.application.user;

import com.green.greengram.application.user.model.UserSignInReq;
import com.green.greengram.application.user.model.UserSignInRes;
import com.green.greengram.application.user.model.UserSignUpReq;
import com.green.greengram.configuration.model.JwtUser;
import com.green.greengram.configuration.model.ResultResponse;
import com.green.greengram.configuration.security.JwtTokenManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    /*파일업로드는 Multipart라는 기술로 파일업로드를 할건데 FE JSON을 보내지만
    * 조금 다른게 받아야 한다. @RequestPart로 받아야한다. @RequestBody 사용 X.
    * req는 파일을 제외한 데이터(uid, upq, nm 데이터들)
    * pic은 프로파일 이미지 파일 */
    @PostMapping("/signup")
    public ResultResponse<?> signUp(@RequestPart UserSignUpReq req
                                    , @RequestPart (required = false)MultipartFile pic) {
        log.info("req: {}", req);
        int result = userService.signUp(req, pic);
        return new ResultResponse<>("회원가입 성공", result);
    }

    @PostMapping("/signin")
    public ResultResponse<?> signIn(HttpServletResponse res, @RequestBody UserSignInReq req) {
        log.info("req: {}", req);
        UserSignInRes userSignInRes = userService.signIn(req);
        //보안 쿠키 처리
        if(userSignInRes != null) {
            JwtUser jwtUser = new JwtUser( userSignInRes.getSignedUserId() );
            jwtTokenManager.issue(res, jwtUser);
        }
        return new ResultResponse<>(userSignInRes == null ? "아이디/비밀번호를 확인해주세요." : "로그인 성공", userSignInRes);
    }
    @PostMapping("/signOut")
    public ResultResponse<?> signOut(HttpServletResponse res) {
        jwtTokenManager.singOut(res);
        return new ResultResponse<>("로그아웃 성공", 1);
    }

}
