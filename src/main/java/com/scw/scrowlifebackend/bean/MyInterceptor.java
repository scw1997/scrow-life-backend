package com.scw.scrowlifebackend.bean;

import com.scw.scrowlifebackend.mojo.Jwt;
import com.scw.scrowlifebackend.mojo.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;


@Slf4j
@Component
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 返回值表示是否继续处理请求
     * true：继续处理请求
     * false：中断请求处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("xxx:{}",request.getRequestURI());
        //对指定请求进行token校验，否则返回401状态码
        String reqUrl = request.getRequestURI();
        String token = request.getHeader("authorization");
        String formatToken = token.replace(" ", "");
        if(token == null|| token.isEmpty() || !Jwt.validateToken(formatToken)) {
            response.setStatus(HttpServletResponse.SC_OK);
            //对象转json
            response.getWriter().write(new ObjectMapper().writeValueAsString(Response.error(401, "Token is invalid or missing")));

            return false;
        }
         return  true;

    }

    /**
     * 在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }



    //视图渲染完毕后执行，最后执行（前后端分离项目则忽略此方法）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO Auto-generated method stub
    }





}
