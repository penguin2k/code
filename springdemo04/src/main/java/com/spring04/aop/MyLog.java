package com.spring04.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
//typy:方法 method:类
//@Target({java.lang.annotation.ElementType.METHOD})
//@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
//@Documented
public @interface MyLog {
    String module() default "";
    String methods() default "";
}
