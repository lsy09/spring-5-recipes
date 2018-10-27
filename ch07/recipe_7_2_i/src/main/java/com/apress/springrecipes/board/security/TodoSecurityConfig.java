package com.apress.springrecipes.board.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 7-2 웹 애플리케이션 로그인 하기
 *
 * 다양한 방법으로 유저가 로그인할 수 있게 지원, HTTP 요청 헤더에 포함된 기본 인증 크레덴셜 처리 기능 역시 이미 스프링 시큐리티에 구현
 * HTTP 기본 인증은 원격 프로토콜, 웹 서비스의 요청을 인증할 때도 쓰임
 */

@Configuration
@EnableWebSecurity
public class TodoSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 기본 보안 구성을 끔
     * super(ture)하면 상위 객체 WebSecurityConfigurerAdapter 생성시
     * 멤버변수 disableDefaults 값이 true로 설정되어 기본 보안 구성이 해제. 기본값은 false
     */
//    public TodoSecurityConfig() {
//        super(true);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").authorities("USER")
                .and()
                .withUser("admin").password("{noop}admin").authorities("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 예외 처리나 보안 컨텍스트 연계 등 스프링 시큐리티의 필수 기능은 인증 기능을 활성화하기전에 켬.
         */
//        http.securityContext()
//                .and()
//                .exceptionHandling();

        /**
         * 서블릿 API 연계 기능도 켜놔야 HttpServletRequest에 있는 메서드를 이용해 뷰에서 뭔가 체크를 할 수 있음
         */
//        http.servletApi();

        http.authorizeRequests()
                .antMatchers("/todos*").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/todos*").hasAuthority("ADMIN")
                .and()
                .formLogin();
    }
}
