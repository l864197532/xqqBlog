package com.xqq.controller;

import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.User;
import com.xqq.enums.AppHttpCodeEnum;
import com.xqq.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.xqq.exception.SystemException;
@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return  blogLoginService.login(user);
    }

    @GetMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }


}
