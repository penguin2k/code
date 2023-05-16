package com.springmvc01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public  class Myadvice {
//    @Pointcut("@annotation(com.spring04.aop.MyLog)")
//    private  void pt(){}
//    @Around("pt()")
//    public  Object log(ProceedingJoinPoint pcj) throws Throwable {
//    long start = System.currentTimeMillis();
//    Object obj = pcj.proceed();
//    long end = System.currentTimeMillis();
//    System.out.println("方法执行时间为："+(end-start));
//        System.out.println("日志调用");
//        recordLog(pcj, (end - start));
//    return obj;
//    }
//    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        MyLog logAnnotation = method.getAnnotation(MyLog.class);
//        log.info("=====================log start================================");
//        log.info("module:{}",logAnnotation.module());
//        log.info("operation:{}",logAnnotation.methods());
//
//        //请求的方法名
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        log.info("request method:{}",className + "." + methodName + "()");
//
////        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        String params = JSON.toJSONString(args[0]);
//        log.info("params:{}",params);
//
//        //获取request 设置IP地址
//        log.info("excute time : {} ms",time);
//        log.info("=====================log end================================");
//    }

//    @Pointcut("execution(* com.spring04.dao.*.*(..))")
//    private  void log1(){}
//    @Around("log1()")
//    public  Object log2(ProceedingJoinPoint pjp) {
//        System.out.println("日志处理：前置通知");
//    Object obj = null;
//
//    @Pointcut("execution(* com.spring04.dao.*.*(..))")
//    private  void log2(){}
//    @Before("log2()")
//    public  void log3(JoinPoint joinPoint){
//        System.out.println("日志处理：前置通知");
//    }
    @Pointcut("execution(* com.spring04.dao.*.*(..))")
    private  void log4(){}
    @Before("log4()")
    public  void log5(JoinPoint joinPoint){
        System.out.println("前置通知：日志处理");
    }
}
