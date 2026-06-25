package com.project.demo.config;

import com.project.demo.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 */
@Configuration
@Slf4j
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截的管理器
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());
        //拦截的地址
        registration.addPathPatterns("/**");
        //方行的地址
//        registration.excludePathPatterns("/**");
        //根据需要拦截，一般设置所有地址拦截，放行公共连接
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /upload/** 请求映射到磁盘上的上传文件目录
        String filePath = System.getProperty("user.dir") + "/target/classes/static/upload/";
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + filePath);
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

}
