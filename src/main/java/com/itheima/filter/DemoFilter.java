package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Slf4j
//@WebFilter(urlPatterns = "/*") //表示拦截所有请求
public class DemoFilter implements Filter {
    @Override  //初始化方法，只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /*
    @Override  //每次拦截到请求之后都会调用，多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("拦截到了请求，放行前的逻辑");
        //放行
        //多个过滤器，按过滤器的类名来排序先后
        filterChain.doFilter(servletRequest, servletResponse ); //放行到下一个过滤器，如果是最后一个则访问资源

        System.out.println("放行后的逻辑");
    }
    */
    @Override  //每次拦截到请求之后都会调用，多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //1.获取请求url
        HttpServletRequest req = (HttpServletRequest) servletRequest;   //强转为http
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getRequestURL().toString();
        log.info("请求的url:"+url);

        //2.判断url是否包含login,如果包含则放行
        if(url.contains("login")){
            log.info("登录操作，放行...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //3.获取请求头中的令牌(token)
        String jwt = req.getHeader("token");

        //4.判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换，将对象转为json返回-------->使用阿里巴巴fastJSON工具包
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);  //将json写入响应
            return;
        }

        //5.如果jwt令牌存在
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {  // 解析失败
            e.printStackTrace();
            log.info("解析失败，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换，将对象转为json返回-------->使用阿里巴巴fastJSON工具包
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);  //将json写入响应
            return;
        }

        //6.解析成功，放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);

    }


    @Override  //销毁方法，只调用一次
    public void destroy() {
        Filter.super.destroy();
    }
}
