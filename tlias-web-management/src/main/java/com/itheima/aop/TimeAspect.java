package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class TimeAspect {

    @Around("com.itheima.aop.MyAspect1.pt()")  //切入点表达式
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
