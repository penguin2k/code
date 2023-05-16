package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Myadvice {
    @Pointcut("execution( )")
    private  void pt(){}
    @Around("pt()")
    public  void method(ProceedingJoinPoint pcj) throws Throwable {
//        Object ret=pcj.proceed(); 有返回值
        pcj.proceed();
    }
}
