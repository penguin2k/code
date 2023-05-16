package com.spring01.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//bean的配置文件
@Configuration
@ComponentScan({"com.spring01.book","com.spring01.service"})//扫描器
@EnableAspectJAutoProxy  //注解开发的aop
public class beanconfig {
}
