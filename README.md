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
