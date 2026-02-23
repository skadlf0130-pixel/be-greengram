package com.green.greengram.application.user;

import com.green.greengram.application.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpReq req);
    UserGetOneRes findByUid(String uid);
    UserProfileGetRes findProfileUser (UserProfileGetReq req);
    UserGetOneRes findById(long signedUserId);
    int updUser(UserUpdDto dto);
}
