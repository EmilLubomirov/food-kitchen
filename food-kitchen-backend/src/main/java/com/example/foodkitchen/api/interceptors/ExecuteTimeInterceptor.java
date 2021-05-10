package com.example.foodkitchen.api.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExecuteTimeInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        long startTime = (Long)request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        if(log.isDebugEnabled()){
            log.debug("[" + handler + "] executeTime : " + executeTime + "ms");
        }
    }
}
