package com.apress.springrecipes.board.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * > 애너테이션을 붙여 메서드 보안하기
 * 메서드보안을 활성화 하려면 구성 클래스에 @EnableGlobalMethodSecurity를 붙이고 @Secured를 사용하기 위해 securedEnabled 속성을 true로 설정
 */

@Configuration
@EnableWebMvc
@ComponentScan(value = "com.apress.springrecipes.board", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableGlobalMethodSecurity(securedEnabled = true)
public class TodoWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/todos");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
