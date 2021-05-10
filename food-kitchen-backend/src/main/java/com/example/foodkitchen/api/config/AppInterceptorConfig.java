package com.example.foodkitchen.api.config;

import com.example.foodkitchen.api.interceptors.ExecuteTimeInterceptor;
import com.example.foodkitchen.api.interceptors.LoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppInterceptorConfig implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;
    private final ExecuteTimeInterceptor executeTimeInterceptor;

    public AppInterceptorConfig(LoggerInterceptor loggerInterceptor, ExecuteTimeInterceptor executeTimeInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
        this.executeTimeInterceptor = executeTimeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(executeTimeInterceptor);
    }
}
