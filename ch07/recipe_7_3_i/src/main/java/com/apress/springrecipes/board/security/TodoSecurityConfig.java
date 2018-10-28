package com.apress.springrecipes.board.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 7-3 유저 인증하기
 * 연쇄적으로 연결된 하나 이상의 AuthenticationManagerBuilder(인증 공급자)를 이요해 인증을 수행
 * 대부분의 인증 공급자는 유저 세부를 보관한 저장소(예: 애플리케이션 메모리, RDBMS, LDAP 저장소)에서 가져온 결과와 대소재 유저를 인증
 */
@Configuration
@EnableWebSecurity
public class TodoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * > 인메모리 방식으로 유저 인증하기
         * 유저세부는 inMemoryAuthentication() 메서드 다음에 한 사람씩 withUser()
         * 메서드를 연결해 유저명, 패스워드, 휴면 상태, 허용 권한을 지정, 휴먼 상태인 유저는 애플리케이션에 로그인 할수 없음.
         */
        auth.inMemoryAuthentication()
                .withUser("admin@ya2do.io").password("secret").authorities("ADMIN","USER").and()
                .withUser("marten@@ya2do.io").password("{noop}user").authorities("USER").and()
                .withUser("jdoe@does.net").password("unknown").disabled(true).authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/todos*").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/todos*").hasAuthority("ADMIN")
            .and()
                .httpBasic().disable()
                .formLogin()
                    .loginPage("/login.jsp")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login.jsp?error=true")
                    .permitAll()
            .and()
                .logout().logoutSuccessUrl("/logout-success.jsp");
    }
}
