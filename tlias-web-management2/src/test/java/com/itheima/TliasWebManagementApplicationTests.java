package com.itheima;

import com.itheima.controller.DeptController;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class TliasWebManagementApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;  //实际就是IOC容器对象

    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptController deptController;

    @Test
    void contextLoads() {
    }

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

    @Test
    public void getTest(){

        Dept dept = deptService.getById(2);
        System.out.println(dept);

    }

    @Test
    public void testGetBean(){
        //根据名称来获取
        DeptController bean1 = (DeptController) applicationContext.getBean("deptController");
        System.out.println(bean1);

        //根据类型获取
        DeptController bean2 = (DeptController) applicationContext.getBean(DeptController.class);
        System.out.println(bean2);

        //根据名称和类型获取
        DeptController bean3 = (DeptController) applicationContext.getBean("deptController", DeptController.class);
        System.out.println(bean3);

        System.out.println(deptController);
    }

}
