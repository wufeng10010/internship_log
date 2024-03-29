package com.itheima.aop;

import com.alibaba.fastjson.JSONObject;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Autowired
    private HttpServletRequest request;  //自动注入请求头，获取里面的jwt令牌

    @Autowired
    private OperateLogMapper operateLogMapper;

//    @Around("@annotation(com.itheima.anno.Log)")
    @Around("execution(* com.itheima.service.*.*(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //操作者id
        String jwt = request.getHeader("token");  //拿到jwt令牌
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer operateUser = (Integer) claims.get("id"); //获取操作人的id

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //操作方法名
        String methodName = joinPoint.getSignature().getName();

        //操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始方法执行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //执行时间
        long costTime = end - begin;

        //记录操作日志
        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className,
                                                methodName, methodParams, returnValue, costTime);
        operateLogMapper.insert(operateLog);
        log.info("记录操作日志：{}", operateLog);

        return result;
    }
}
