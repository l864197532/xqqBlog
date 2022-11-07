package com.xqq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xqq.constants.SystemConstants;
import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Article;
import com.xqq.domin.entity.Category;
import com.xqq.domin.vo.CategoryVo;
import com.xqq.mapper.CategoryMapper;
import com.xqq.service.ArticleService;
import com.xqq.service.CategoryService;
import com.xqq.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-11-07 14:17:23
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为正常
        LambdaQueryWrapper<Article> articlewrapper = new LambdaQueryWrapper<>();
        articlewrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);//正式文档
        List<Article> articleList = articleService.list(articlewrapper);

        Set<Long> categoeryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        List<Category> categories = listByIds(categoeryIds);

        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        List<CategoryVo> categoryVos = null;
        try {
            categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseResult.okResult(categoryVos);
    }
}
