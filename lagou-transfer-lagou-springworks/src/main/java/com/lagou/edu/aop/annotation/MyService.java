package com.lagou.edu.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangdm
 * @description 模拟@Service注解
 */
//可以在类上标注
@Target(ElementType.TYPE)
//运行时保留此注解信息
@Retention(RetentionPolicy.RUNTIME)
public @interface MyService {
    String value() default "serviceName";
}
