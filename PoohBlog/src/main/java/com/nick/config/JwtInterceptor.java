package com.nick.config;

import com.nick.util.jwtUtil.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class JwtInterceptor implements HandlerInterceptor {
    //return true执行下一个拦截器，放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String jwt=request.getHeader("Authorization");
        if(jwt==null)
        {
            return false;
        }
        Claims claims= JwtUtils.parseJWT(jwt);
        //若token不符合直接拦截
        if(claims==null){
            return false;
        }
        else
        {
            //isNotExpired 返回true表示未过期 不需要拦截
            //isNotExpired 返回false表示已经过期，拦截
            Boolean isNotExpired=JwtUtils.isNotExpired(claims);
            if(isNotExpired==false) {
                return isNotExpired;
            }
            return isNotExpired;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
