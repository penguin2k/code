package com.springmvc01.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//bean的配置文件
@Configuration
@ComponentScan({"com.springmvc01.dao","com.springmvc01.service","com.springmvc01.aop"})//扫描器
@EnableAspectJAutoProxy  //注解开发的aop
public class beanconfig {
}
