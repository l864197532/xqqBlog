package com.xqq;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xqq.mapper")
public class XqqBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XqqBlogApplication.class,args);
    }

}
