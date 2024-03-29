package com.itheima.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //指定这个注解什么时候生效（运行时有效）
@Target(ElementType.METHOD) //这个注解可以作用在哪些地方（方法上）
public @interface Log {
}
