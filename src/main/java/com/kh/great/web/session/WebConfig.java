package com.kh.great.web.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //인터셉터 등록
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //모든 유청에 대한 log
//        registry.addInterceptor(new Log_Interceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/error");
//        //로그인 인증 체크(세션체크)
//        List<String> whiteList = new ArrayList<>();
//
//        whiteList.add("/");
//        whiteList.add("/login");
//        whiteList.add("/logout");
//        whiteList.add("/error");
//        whiteList.add("/**");
//
//        registry.addInterceptor(new LogIn_Interceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns(whiteList);
//
//    }
}
