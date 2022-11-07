package com.xqq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-11-07 16:05:09
 */
public interface LinkService extends IService<Link> {


    ResponseResult getAllLink() throws Exception;
}
