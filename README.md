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

## jwt令牌
全称json web token,定义了一种简洁的自包含的格式，用于在通信双方以json数据格式安全的传输信息。由于数字签名的存在，这些信息是可靠的
### 组成
* Header(头),记录令牌类型，算法等
* Payload(有效载荷)，携带一些自定义、默认信息等
* Signature(签名)，防止token被篡改，确保安全性，将header,payload融入并加入指定秘钥
### 应用场景：登录
登录成功，生成令牌，后续每个请求都要携带jwt令牌，系统在处理请求时都需先校验令牌。
<img width="1032" alt="image" src="https://github.com/wufeng10010/jinqiao_log/assets/131955051/11d68a18-fb0c-45f2-8309-3004dc4e6919">
## 生成jwt令牌
在pom.xml文件总导入jwt令牌的依赖
```java
<!--        jwt令牌依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
```
在测试类中测试jwt令牌的生成
```java
@Test
public void testGenJwt(){
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", 1);
    claims.put("name", "tom");

    String jwt = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, "itheima")  //签名算法
            .setClaims(claims)  //自定义内容(载荷)
            .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))  //设置令牌的有效期
            .compact();

    System.out.println(jwt);
}
```
输出结果：
```java
eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcxMDc1MjYzM30.OgFoQdr3StCiswwh0r5c7-O9cf4hFCfkECSPjl8AhRI
```
