package com.xqq.service;

import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
