package com.springmvc01.aop;

//typy:方法 method:类
//@Target({java.lang.annotation.ElementType.METHOD})
//@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
//@Documented
public @interface MyLog {
    String module() default "";
    String methods() default "";
}
