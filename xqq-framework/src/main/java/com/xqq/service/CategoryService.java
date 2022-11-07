package com.xqq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xqq.domin.ResponseResult;
import com.xqq.domin.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-11-07 14:17:22
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
