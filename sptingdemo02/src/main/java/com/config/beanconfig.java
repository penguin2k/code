package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//bean的配置文件
@Configuration
@ComponentScan({"com.dao","com.service"})//扫描器
@EnableAspectJAutoProxy  //注解开发的aop
public class beanconfig {
}
