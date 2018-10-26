package com.apress.springrecipes.board.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 *  스프링 시큐리는 HTTP 요청에 서블릿 필터를 적용해 보안을 처리
 *  AbstractSecurityWebApplicationInitializer라는 베이스 클래스를 상속 하면 편리하게 필터를 등록하고 구성내용이 자동 감지
 *
 *  WebSecurityConfigurerAdapter라는 구성 어댑터에 준비된 다양한 configure() 메서드를 이용
 *  * 폼 기반 로그인 서비스(form-based login service) : 유저가 애플리게이션에 로그인하는 기본 폼 페이지 제공
 *  * HTTP 기본 인증(Basic authentication) : 요청 헤더에 표시된 HTTP 기본 인증 크레덴셜을 처리
 *
 *
 *
 *  스프링 시큐리티가 사용하는 필터를 등록, AbstractSecurityWebApplicationInitializer 클래스를 상속
 *  AbstractSecurityWebApplicationInitializer 생서자는 하나 이상의 구성 클래스를 이수로 받아 보안 기능을 가동
 *
 *  *AbstractSecurityWebApplicationInitializer를 상속한 클래스가 있으면 그 클래스에 보안 구성을 추가, 그렇지 않으면 애플리케이션 시동 중 예외 가 발생
 */
public class TodoSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public TodoSecurityInitializer() {
        super(TodoSecurityConfig.class);
    }
}
