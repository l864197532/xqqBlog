package com.xqq.controller;

import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Article;
import com.xqq.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return  articleService.list();
//    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() throws Exception {
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) throws Exception {
       return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public  ResponseResult getArticleDetail(@PathVariable("id") long id){
        return  articleService.getArticleDetail(id);
    }


}
