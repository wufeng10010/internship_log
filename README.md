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

## 事务
### 概念
是一组操作的集合，它是一个不可分割的工作单位，这些操作要么同时成功，要么同时失败
### 操作
* 开启事务（一组操作开始前，开启事务）
* 提交事务（这些操作全部成功后，提交事务）
* 回滚事务（中间一个操作出现异常，回滚事务，阻止数据出错） 这是事务处理的核心部分！！

### spring事务管理日志
```.yml
logging:
  level:
    org.springframework.jdbc.support.JdbcTransactionManager: debug
```
