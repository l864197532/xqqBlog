package com.xqq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xqq.constants.SystemConstants;
import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Article;
import com.xqq.domin.entity.Category;
import com.xqq.domin.vo.ArticleDetailVo;
import com.xqq.domin.vo.ArticleListVo;
import com.xqq.domin.vo.HotArticleVo;
import com.xqq.domin.vo.PageVo;
import com.xqq.mapper.ArticleMapper;
import com.xqq.service.ArticleService;

import com.xqq.service.CategoryService;
import com.xqq.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() throws Exception {
        //查询热门文章，封装返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//正式文档
        queryWrapper.orderByDesc(Article::getViewCount);//浏览量降序排序
        Page<Article> page = new Page<>(1,10);//第一页，一页10个数据
        List<Article> articles = page(page, queryWrapper).getRecords();

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) throws Exception {
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();

        //如果有categoryId
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//正式文档
        queryWrapper.orderByDesc(Article::getIsTop);//浏览量降序排
        Page<Article> page = new Page<>(pageNum,pageSize);
        List<Article> articles = page(page, queryWrapper).getRecords();
//        for (Article article : articles) {
//            article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
//        }
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据文章id 查数据
        Article article = getById(id);
        //封装vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailVo);
    }
}
