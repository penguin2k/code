package com.spring04.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//bean的配置文件
@Configuration
@ComponentScan({"com.spring04.dao","com.spring04.service","com.spring04.aop"})//扫描器
@EnableAspectJAutoProxy  //注解开发的aop
public class beanconfig {
}
