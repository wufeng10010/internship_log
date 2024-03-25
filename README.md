# 2.27
主要学习了maven的概念，以及在idea中的配置和使用,并成功在idea中配置完成

# 2.28
学习网络编程的相关概念，主要学习了UDP，TCP协议，以及在java代码中实现了相关小demo

# 2.29
在idea中创建springboot项目，并运行了一个web应用小demo，之后学习请求响应相关知识

# 3.4
使用分层解耦的方式来搭建一个springboot的小demo，了解了IOC（控制反转）和DI（依赖注入）的设计思想

# 3.6 
mybatis基础操作(预编译SQL)

# 3.8
使用mybatis操控数据库

# 3.14
springboot配置文件

# 3.18
jwt令牌以及filter过滤器

# 3.22
Interceptor拦截器

# 3.25
事务管理

# 3.27
AOP:Aspect Oriented Programming（面向切面编程，面向方面编程），其实就是面向方法的编程

### 实现
动态代理是面向切面编程最主流的实现

先通过一个场景来大致了解一下AOP的使用，例如我们需要统计每一个业务方法（如增删改查）的执行耗时，如果在每个方法上增加一段计时的代码，则进行了大量的重复工作，此时我们就可以使用AOP来执行这段逻辑：

需要在pom.xml导入AOP的依赖
```.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
编写AOP程序：针对特定方法根据业务需要进行编程，如这段统计计时的代码：
```java
public class TimeAspect {

    @Around("execution(* com.itheima.service.*.*(..))")  //切入点表达式
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录开始时间
        long begin = System.currentTimeMillis();
        
        //调用原始方法运行
        Object result = joinPoint.proceed();

        //记录结束时间
        long end = System.currentTimeMillis();

        log.info(joinPoint.getSignature() + "方法执行耗时：{}ms", end - begin);

        return result;
    }
}
```
注意在这段代码中使用的是@Around注解，需要使用joinPoint.proceed()来调用原始代码执行，并且将返回值返回，返回类型为Object，

AOP的优势就在于避免大量代码的侵入编写，减少重复代码，提高开发效率以及便于维护

### AOP核心概念
连接点JoinPoint，可以被AOP控制的方法
通知Advice，指哪些重复的逻辑，也就是共性功能，如上述的计时操作
切入点PointCut，匹配连接点的条件，通知仅会在切入点方法执行时被应用
切面Aspect，描述通知与切入点的对应关系，实际就是（通知+切入点）
目标对象Target，通知所应用的对象
