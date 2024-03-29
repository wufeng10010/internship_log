package com.itheima.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override  //目标资源方法运行前运行，返回true放行，false不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求url
//        HttpServletRequest req = (HttpServletRequest) servletRequest;   //强转为http
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        log.info("请求的url:"+url);

        //2.判断url是否包含login,如果包含则放行
        if(url.contains("login")){
            log.info("登录操作，放行...");
            return true;
        }

        //3.获取请求头中的令牌(token)
        String jwt = request.getHeader("token");

        //4.判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换，将对象转为json返回-------->使用阿里巴巴fastJSON工具包
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);  //将json写入响应
            return false;
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
            response.getWriter().write(notLogin);  //将json写入响应
            return false;
        }

        //6.解析成功，放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override  //目标资源方法运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override  //视图渲染完毕后运行，最后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
