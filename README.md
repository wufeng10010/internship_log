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

## 实现
动态代理是面向切面编程最主流的实现

先通过一个场景来大致了解一下AOP的使用，例如我们需要统计一些业务方法（如增删改查）的执行耗时，如果在每个方法上增加一段计时的代码，则进行了大量的重复工作，此时我们就可以使用AOP来执行这段逻辑：

首先需要在pom.xml导入AOP的依赖
```.xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
然后编写AOP程序：针对特定方法根据业务需要进行编程，如这段统计计时的代码：
```java
@Component
@Slf4j
@Aspect
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
这样，我们就可以在运行com.itheima.service包下的所有类的所有方法时，都能统计到它们各自的运行时间，如以下测试：
```java
@Test
public void getTest(){
    Dept dept = deptService.getById(2); //获取id为2的部门数据
}
```
控制台中就可以得到如下结果：
<img width="573" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/1b1727a6-7481-4495-b9af-80d14dbdd7e1">

以上用例展示了AOP的优势：避免大量代码的侵入编写，减少重复代码，提高开发效率以及便于维护

## AOP核心概念
**连接点JoinPoint**，可以被AOP控制的方法

**通知Advice**，指哪些重复的逻辑，也就是共性功能，如上述的计时操作

**切入点PointCut**，匹配连接点的条件，通知仅会在切入点方法执行时被应用（可以认为是连接点的子集）

**切面Aspect**，描述通知与切入点的对应关系，实际就是（通知+切入点）

**目标对象Target**，通知所应用的对象

## 通知类型
@Around:环绕通知，在目标方法前、后都被执行 **这个通知必须要用ProceedingJoinPoint.proceed()来调用原始代码执行,且需要Object来接收原始方法的返回值并进行返回**

@Before:前置通知，在目标方法前执行

@After：后置通知，在目标方法后执行，无论是否有异常都会执行

@AfterReturning:返回后通知，在目标方法后执行，有异常不会执行

@AfterThrowing:异常后通知，在发生异常后执行

## 切入点表达式抽取
如果存在多个通知，且通知的切入点都是相同的，则可以把切入点表达式进行抽取，来对通知方法上的@Around("execution(。。。)")进行简化
### 第一种方式，使用@Pointcut注解

可以在切面类中定义一个方法，加上@Pointcut注解，在注解里写上需要抽取的相同的切入点表达式：
```java
@Pointcut("execution(* com.itheima.service.*.*(..))")
public void pt(){}
```
这样，通知方法上的注解就可以由：
```java
@Around("execution(* com.itheima.service.*.*(..))")
```
变为：
```java
@Around("pt()")
```
如上述的记录时间切面类就可以改为：
```java
@Component
@Slf4j
@Aspect
public class TimeAspect {

    @Pointcut("execution(* com.itheima.service.*.*(..))")  //在此处定义切入点表达式
    public void pt(){}

    @Around("pt()")  //切入点
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

    @Around("pt()")  //切入点
    public 其他通知方法。。。
}
```
#### 切入点表达式execution匹配规则
execution主要根据方法的返回值、包名、类名、方法名、方法参数来匹配<img width="598" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/08fbb3be-ee25-48ac-9ac1-5d4dae9a1d12">
带问号部分可省略

可以使用通配符描述切入点

*：单个独立的任意符号，可以通配任意返回值，包名，类名，方法名，任意类型的**一个**参数，也可以通配包名、类名、方法名的一部分（前缀后缀）

.. : 可以通配任意层级的包，或任意类型任意个数的参数

### 第二种方式，使用@annotation注解
该注解用于标识有特定注解的方法（这个特定注解由我们自己定义），如我们定义一个以下特定注解：
```java
@Retention(RetentionPolicy.RUNTIME) //指定这个注解什么时候生效（运行时有效）
@Target(ElementType.METHOD) //这个注解可以作用在哪些地方（方法上）
public @interface Log {
}
```
然后在通知上加上注解@Around("@annotation(...)")，...是自定义注解@Log的全类名

如果我们想要这个通知匹配如增删改的方法，直接在这些方法上加入自定义注解@Log：
```java
@Log
public void deleteById(Integer id){
    删除业务操作
}
@Log
public void add(Dept dept) {
    增加业务操作
}
@Log
public void update(Dept dept) {
    更改操作
}
```

## AOP案例-记录操作日志
#### 操作日志
日志信息包括操作人id，操作时间，执行方法的全类名，执行方法名，方法运行参数、返回值，方法执行时长

首先在数据库中创建一张操作日志表
<img width="885" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/4aa712e6-fa9f-44a9-8045-36a342e124a4">

创建操作日志的pojo实体类OperateLog：
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog {
    private Integer id; //ID
    private Integer operateUser; //操作人ID
    private LocalDateTime operateTime; //操作时间
    private String className; //操作类名
    private String methodName; //操作方法名
    private String methodParams; //操作方法参数
    private String returnValue; //操作方法返回值
    private Long costTime; //操作耗时
}
```
创建操作日志的Mapper接口，实现操作日志的插入操作
```java
@Mapper
public interface OperateLogMapper {
    //插入日志数据
    @Insert("insert into operate_log (operate_user, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateUser}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);
}
```
创建操作日志的切面类:
```java
@Aspect
@Slf4j
@Component
public class LogAspect {

    @Autowired
    private HttpServletRequest request;  //自动注入请求头，获取里面的jwt令牌

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.itheima.anno.Log)")
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
```
然后在相关的执行方法上加上@Log注解，标记此方法为切入点，最后启动服务进行测试
<img width="1210" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/81297d22-b0bf-42f6-814c-cfee4cb3303c">
在前端页面登录后并点击相关操作，查看数据库
<img width="1197" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/c94168d1-91eb-44d4-b4b3-90f27fada9e4">
相关操作都日志都已经被记录下来，测试完成
