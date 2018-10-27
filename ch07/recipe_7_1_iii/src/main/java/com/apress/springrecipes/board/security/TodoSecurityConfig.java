package com.apress.springrecipes.board.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * > URL 접근 보안하기
 * WebSecurityConfigurerAdapter 클래스에 있는 configure(HttpSecurity http)
 * 이 메서드는 기본적으로 anyRequest().authenticated()해서 매번 요청이 들어올 때 마다 반드시 인증을 받도록 강제함
 * 또 HTTP 기본 인증(httpBasic())및 폼 기반 로그인(formLogin()) 기능은 기본적으로 켬
 */

@Configuration
@EnableWebSecurity
public class TodoSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 인증 서비스는 configure(AuthenticationManagerBuilder auth) 메서드를 오버라이드 해서 구성
     * 스프링 시큐리티는 DB 또는 LDAP 저장소를 조회하여 유저를 인증하는 몇가지 방법을 지원
     * 보안 요건이 단순하다면 유저마다 이름, 패스워드, 권한 등 유저 세부(user details)를 직접 지정
     *
     * 접두어 {noop}는 내부적으로 NoOpPasswordEncoder를 사용하겠다는 선언(비권장 클래스:예제 실행 위해 작성함)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").authorities("USER")
                .and()
                .withUser("admin").password("{noop}admin").authorities("USER", "ADMIN");
    }

    /**
     * configure(HttpSecurity http) 메서드를 오버라이드 하면 더 정교한 인가 규칙을 적용
     *
     * URL 접근 보안은 authorizeRequests()부터 시작, 여러 가지 매처(matchers)를 이용해 규칙을 정할 수 있음
     * URL 패턴 끝의 와일드카드를 빼면 쿼리 매개변수가 있는 URL은 걸리지 않으니 주의
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/todos*").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/todos*").hasAuthority("ADMIN")
            .and()
                .formLogin()
            .and()
                .csrf().disable();
    }
}
