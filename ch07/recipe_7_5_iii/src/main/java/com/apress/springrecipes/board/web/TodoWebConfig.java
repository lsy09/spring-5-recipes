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
 * > 애너테이션 + 표현식으로 메서드 보안하기
 * 메서드 보안에서도 좀 더 정교한 보안 규칙을 적용하고 싶다면 @PreAuthorize, @PostAuthorize 같은 애너테이션에 SpEL 기반의 보안 표현식을 작성
 * 두 애너테이션을 이용하려면 @EnableGlobalMethodSecurity의 prePostEnabled 속성을 true으로 설정
 * @PreAuthorize믄 메서드 호출 직전, @PostAuthorize는 메서드 호출 직후 각각 작동
 * 보안 표현식을 작성해 적용하거나 returnObject 표현식으로 메서드 호출 결과를 받아올 수도 있음
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.apress.springrecipes.board", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
