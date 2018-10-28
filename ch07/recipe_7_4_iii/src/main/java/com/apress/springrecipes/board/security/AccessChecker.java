package com.apress.springrecipes.board.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * > 스프링 빈을 표현식에 넣어 접근 통제 결정하기
 * @accessChecker.hasLocalAccess(authentication) 표현식으로 Authentication 객체를 받는 hasLocalAccess() 메서드를 표현식에서 호출할 수 있음
 * AccessChecker 클래스는 IpAddressVoter나 커스텀 표현식 핸들러와 기능은 같지만 스프링 시큐리티 클래스를 상속하지 않음
 */
public class AccessChecker {

    public boolean hasLocalAccess(Authentication authentication) {
        boolean access = false;
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            String address = details.getRemoteAddress();
            access = address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1");
        }
        return access;
    }
}
