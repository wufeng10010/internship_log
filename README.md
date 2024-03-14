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

在创建一个springboot项目时，会自动生成一个配置文件application.properties，后缀名为.properties，在里面可以配置我们的各种属性，如数据库相关信息
```properties
#驱动类名称
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#数据库连接的url
spring.datasource.url=jdbc:mysql://localhost:3306/demo01
#连接数据库的用户名
spring.datasource.username=root
#连接数据库的密码
spring.datasource.password=123456
```
一般推荐使用后缀为.yml类型的配置文件，因为.properties的配置文件结构层级不清晰，而.yml则较为简洁，以数据为中心，上述代码在application.yml中的样式为：
```yml
spring:
#  数据库的连接信息
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatis
    username: root
    password: 123456
```
需要注意的是数值前面要有空格（即冒号后），使用空格来缩进表示层级关系，缩进的空格数目不重要，只有相同层级的元素左边对齐即可
### yml数据格式
* 对象/Map集合
```yml
user:
  name: zhangsan
  age: 18
  password: 123456
```
* 数组/List/Set集合
```yml
hobby
  - Java
  - C++
  - Python
```

一个重要的注解@Value，通常用于外部配置的属性注入，具体用法为：@Value("${配置文件中的key}")

例如在文件上传时，如果要使用阿里云OSS时，则需要参数，如果我们直接将参数封装到类中（如下）则非常不便维护和管理
```java
@Component
public class AliOSSUtils {

    private String endpoint = "https://oss.console.aliyun.com";

    private String accessKeyId = "LTAI5tNnkubuobYrcwVzRxuN";

    private String accessKetSecret = "YP5cIF7nN5KOgpI61cCFKLTZtD";

    private String bucketName = "web-tlias-wufeng";
}
```
所以我们可以在配置文件中将这些参数配置好
```yml
aliyun:
  oss:
    endpoint: ttps://oss.console.aliyun.com
    accessKeyId: LTAI5tNnkubuobYrcwVzRxuN
    accessKetSecret: YP5cIF7nN5KOgpI61cCFKLTZtD
    bucketName: web-tlias-wufeng
```
然后就可以在AliOSSUtils类中使用@Value注解
```java
@Component
public class AliOSSUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKetSecret}")
    private String accessKetSecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
}
```
当然，使用@Value注解仍然会显得有些繁琐臃肿，则可以使用@ConfigurationProperties(prefix=前缀)注解，直接在类上加上这个注解，声明在配置文件这些属性值的前缀
```java
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSUtils {

    private String endpoint;

    private String accessKeyId;

    private String accessKetSecret;

    private String bucketName;
}
```
