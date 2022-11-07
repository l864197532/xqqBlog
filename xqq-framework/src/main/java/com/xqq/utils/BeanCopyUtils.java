package com.xqq.utils;

import com.xqq.domin.entity.Article;
import com.xqq.domin.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    public BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    public static<O,V> List<V> copyBeanList(List<O> list, Class<V> clazz ) throws Exception {

       return list.stream()
                .map(o-> copyBean(o,clazz))//转换
                .collect(Collectors.toList());//收集0

    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("ss");
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }
}
