package com.xqq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Article;


public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList() throws Exception;

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) throws Exception;

    ResponseResult getArticleDetail(Long id);
}
