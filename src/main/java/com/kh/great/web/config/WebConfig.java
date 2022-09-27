package com.kh.great3;

import com.kh.great3.web.interceptor.LogInterceptor;
import com.kh.great3.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        //모든 요청에 대한 log
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/error");

        //로그인 인증 (세션체크)
        List<String> whiteList = new ArrayList<>();
        whiteList.add("/css/**");
        whiteList.add("/js/**");
        whiteList.add("/img/**");
        whiteList.add("/member/**");
        whiteList.add("/login");
        whiteList.add("/logout");
        whiteList.add("/");
        whiteList.add("/join");
        whiteList.add("/joinComplete");
        whiteList.add("/findId");
        whiteList.add("/findPw");
        whiteList.add("/resetPw");

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(whiteList);
    }
}
