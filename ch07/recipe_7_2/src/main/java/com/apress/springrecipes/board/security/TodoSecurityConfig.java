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
    public TodoSecurityConfig() {
        super(true);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").authorities("USER")
                .and()
                .withUser("admin").password("{noop}admin").authorities("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/todos*").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/todos*").hasAuthority("ADMIN")
            .and()
                .anonymous()
                /**
                 * 서블릿 API 연계 기능도 켜놔야 HttpServletRequest에 있는 메서드를 이용해 뷰에서 뭔가 체크를 할 수 있음
                 */
            .and()
                .servletApi()
            .and()
                .securityContext()
            .and()
                /**
                 * 예외 처리나 보안 컨텍스트 연계 등 스프링 시큐리티의 필수 기능은 인증 기능을 활성화하기전에 켬.
                 */
                .exceptionHandling()
            .and()
                /**
                 * > 리멤버 미 기증
                 * rememberMe() 메서드로 구성
                 * 유저명, 패스워드, 리멤버미 만료 시각, 개인키를 하나의 토큰으로 인코딩해서 유저 브라우저 쿠키로 저장
                 * 정적인 리멤버 미 토큰은 해커가 얼마든지 빼낼 수 있어 잠재적 보안 이슈가 있음.
                 * 스프링 시큐리티는 토큰을 회전(롤링rolling)시키는 고급 기술도 지원하지만 이렇게 하려면 토큰을 보관할 별도의 DB가 별도로 필요힘
                 */
                .rememberMe()
            .and()
                /**
                 * > 폼 기반 로그인
                 * formLogin()메서드로 폼 기반 로그인 서비스를 구성하면 유저가 로그인 정보를 입력하는 폼 페이지가 자동 렌더링
                 * 기본 로그인 페이지 URL은 /login. 애플리케이션 페이지에서는 URL으로 링크를 걸어 유저를 로그인 페이지로 유도
                 */
                .formLogin()
                    .loginPage("/login.jsp")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login.jsp?error=true")
                    /**
                     * 보안 URL을 요청하면 로그인 페이지로 넘어가고 여기서 로그인을 하면 대상 URL으로 리다아렉트되는것이 수순
                     * 그런데 유저가 로그인 페이지 URL로 바로 접속한 경우, 유저는 로그인 직후 컨텍스트 루트(http://localhost:8080/todos/)로 리다이렉트
                     */
                    .defaultSuccessUrl("/todos")
                .permitAll()
            .and()
                /**
                 * > 로그아웃 서비스
                 * 기본 URL은 /logout 이면 POST 요청일 경우에만 작동
                 * 기본 경로인 컨텍스트 루트로 이동 하지 않고 다른 URL으로 보내고 싶다면 logoutSuccessUrl() 메서드에 지정
                 */
                .logout().logoutSuccessUrl("/logout-success.jsp")
            .and()
                /**
                 * 로그아웃 이후 브라우저에서 '뒤로가기'하면 이미 로그아웃을 한 상태지만 다시 로그인된 이전 페이지로 돌아가는 모순이 발생
                 * headers()메서드로 보안 헤더를 활성화하면 브라우저가 더 이상 페이지를 캐시 하지 않음.
                 */
                .headers()
            .and()
                .csrf()
            .and()
                /**
                 * > HTTP 기본 인증
                 * HTTP 기본 인증은 HttpBasic() 메서드로 구성, HTTP 기본 인증을 적용하면 대부분의 브라우저는 로그인 대화상자를 띄우거나
                 * 유저를 특정 로그인 페이지로 이동 시켜 로그인을 유도
                 */
                .httpBasic()
            .and()
                /**
                 * > 폼 기반 로그인
                 * formLogin() 메서드로 폼 기반 로그인 서비스를 구성하면 유저가 로그인 정보를 입력하는 폼 페이지가 자동으로 렌더링 됨
                */
                .formLogin()
            .and()
                /**
                 * > 익명 로그인 구하기
                 * 익명 로그인 서비스는 anonymous() 메서드에 유저명(기본값은 anonymousUser)과 익명 유저의 권한(기본값은 ROLE_ANONYMOUS)을 지정
                 */
                .anonymous().principal("guest").authorities("ROLE_GUEST");

        ;
    }
}
