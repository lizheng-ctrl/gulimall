package com.atguigu.gulimall.auth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.rmi.registry.Registry;

@Configuration
public class GulimallWebConfig implements WebMvcConfigurer {

    /**
     *     @GetMapping("/login.html")
     *     public String loginPage(){
     *         return "login";
     *     }
     *
     *     @GetMapping("reg.html")
     *     public String regPage(){
     *         return "reg";
     *     }
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/reg.html").setViewName("reg");
    }

}
