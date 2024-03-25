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
在程序出现异常时如何进行回滚事务，以删除数据库中一个表项为例：
```java
@Transactional //将当前方法交给spring管理，方法开启前开启事务，提交or回滚事务(如果异常的话)
@Override
public void deleteById(Integer id){
  deptMapper.deleteById(id);  //根据id来删除部门
  int i = 1/0;        //模拟异常
  empMapper.deleteByDeptId(id);  //同时删除部门里的员工
}
```
以上代码中模拟了一个异常，如果部门数据被删除，则与之相关的员工数据也应该被删除，但如果在其中出现了异常，则可能会造成最终数据出错。

使用@Transactional注解则可以回滚事务

但如果将以上代码更改为：
```java
@Transactional
@Override
public void deleteById(Integer id) throws Exception {
  deptMapper.deleteById(id);  //根据id来删除部门
  //模拟异常
  if (true) {
            throw new Exception("出错。。。");
        }
  empMapper.deleteByDeptId(id);  //同时删除部门里的员工
}
```
则并没有发生回滚事务，这是因为默认情况下，只有RuntimeException（运行时异常）才回滚异常。如果想让所有的异常都进行回滚，则可以可以用@Transactional注解中的rollbackFor控制
```java
@Transactional(rollbackFor = Exception.class) //所有异常类型
```

### propagation
事务传播行为：指当一个事务方法被另一个事务方法调用时，这个被调用的事务方法如何进行事务控制

此时需要用到@Transactional注解中的propagation属性值
<img width="972" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/4f1b164f-befe-4e50-bd31-090d49663040">

主要使用到的两种：
* REQUIRED:大部分情况下使用
* REQUIRES_NEW:当不需要事之间相互影响时可以使用该传播行为，比如需要记录日志时，无论是否发生异常，都希望日志记录能够成功

