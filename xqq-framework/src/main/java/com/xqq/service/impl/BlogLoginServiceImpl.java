package com.xqq.service.impl;

import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.LoginUser;
import com.xqq.domin.entity.User;
import com.xqq.domin.vo.BlogUserLoginVo;
import com.xqq.domin.vo.UserInfoVo;
import com.xqq.enums.AppHttpCodeEnum;
import com.xqq.service.BlogLoginService;
import com.xqq.utils.BeanCopyUtils;
import com.xqq.utils.JwtUtil;
import com.xqq.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        BlogUserLoginVo blogUserLoginVo=new BlogUserLoginVo();
        UserInfoVo userInfoVo  = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        blogUserLoginVo.setUserInfo(userInfoVo);
        blogUserLoginVo.setToken(jwt);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
}
