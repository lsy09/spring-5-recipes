package com.apress.springrecipes.board.security;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 7-4 접근 통제 결정하기
 * 유저가 리소스에 접근 가능한지 판단하는 행위로서 유저 인증 상태와 리소스 속성에 따라 좌우
 * 스프링 시큐리티에서는 AccessDecisionVoter 인터페이스를 구현한 접근 통제 결정 관리자가 판단
 * 스프링 시큐리티는 거수(손들어 투표하기) 방식으로 작동하는 세 가지 간편한 접근 통제 결정 관리자를 기본 제공
 * AccessDecisionVoter 인터페이스를 구현하며 유저의 리소스 접근 요청에 대해 AccessDecisionVoter 인터페이스의 상수 필드로
 * 찬성(ACCESS_GRANTED), 기권(ACCESS_ABSTAIN), 반대(ACCESS_DENIED)의사를 표명
 *
 * 별도로 접근 통제 결정 관리자를 명시하지 않으면 스프링 시큐리티는 AffirmativeBased를 기본 접근 통제 결정 관리자로 임명하고 두거수기를 구성
 * * RoleVoter : 유저 롤을 기준으로 접근 허용 여부를 거수 ROLE_접두어 로 시작하는 접근 속성만 처리하며 유저가 리소스 접근에 필요한 롤과 동일한 롤을
 * 보유하고 있으면 찬성, 하나라도 부족하면 반대. ROLE_로 시작하는 접근 속성이 하나도 없는 리소스에 대해서는 기권표를 던짐
 * * AuthenticatedVoter : 유저 인증 레벨을 기준으로 접근 허용 여부를 거수하며 IS_AUTHENTICATED_FULLY, IS_AUTHENTICATED_REMEMBERD,
 * IS_AUTHENTICATED_ANONYMOUSLY 세가지 접근 속성만 처리 인증 레벨은 전체 인증(fully authenticated, 유저를 완전히 인증)이 가장 높고
 * 그다음은 자동 인증 (authentication remembered, 자동 인증을 거쳐 인증), 익명 인증(anonymously authenticated) 순서
 *
 * > 표현식을 이용해 접근 통제 결정하기
 * AccessDecisionVoter로도 어느정도 유연하게 접근 통제 결정을 내릴 순 있지만 더 정교하고 복잡한 접근 통제 규칙을 적용해야 한다면
 * SpEL(Springs Expression Language 스프링 표현식 언어)을 사용
 * WebExpressionVoter 거수기를 거느린 접근 통제 결정 관리자를 빈으로 자동 구성
 */

public class IpAddressVoter implements AccessDecisionVoter<Object> {

    private static final String IP_PREFIX = "IP_";
    private static final String IP_LOCAL_HOST = "IP_LOCAL_HOST";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return (attribute.getAttribute() != null) && attribute.getAttribute().startsWith(IP_PREFIX);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> configList) {
        if (!(authentication.getDetails() instanceof WebAuthenticationDetails)) {
            return ACCESS_DENIED;
        }

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String address = details.getRemoteAddress();

        int result = ACCESS_ABSTAIN;

        for (ConfigAttribute config : configList) {
            result = ACCESS_DENIED;

            /**
             * 접두어 IP_로 시작하는 접근 속성만 대상으로 삼고 그중 IP_LOCAL_HOST 접근 속성에 한하여 유저의 IP 주소가 127.0.0.1
             * 또는 0:0:0:0:0:0:0:1(리눅스 워크스테이션일 경우)이면 찬성, 그렇지 않으면 반대, 리소스에 IP_로 시작하는 접근 속성이 없으면 그냥 넘어감
             */
            if (Objects.equals(IP_LOCAL_HOST, config.getAttribute())) {
                if (address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1")) {
                    return ACCESS_GRANTED;
                }
            }
        }

        return result;
    }
}
