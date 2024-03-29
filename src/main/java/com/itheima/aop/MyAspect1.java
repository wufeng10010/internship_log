package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@Aspect
public class MyAspect1 {

    @Pointcut("execution(* com.itheima.service.*.*(..))")
    public void pt(){}

    @Around("pt()")  //切入点表达式
    public Object timeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("around before");

        //获取目标对象类名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("目标方法类名：" + className);

        //获取目标方法方法名
        String methodName = joinPoint.getSignature().getName();
        log.info("目标方法方法名：" + methodName);

        //获取目标方法传入参数
        Object[] args = joinPoint.getArgs();
        log.info("目标方法传入参数为：" + Arrays.toString(args));

        //调用原始方法运行
        Object result = joinPoint.proceed();

        //获取目标方法运行返回值
        log.info("目标方法运行返回值：" + result);

        log.info("around after");

        return result;  // 这一步可以对目标结果进行篡改
    }

//    @After("execution(* com.itheima.service.*.*(..))")  //切入点表达式
//    public void after(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//        log.info("after");

//}
}
