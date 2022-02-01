package org.rumblekat.club.security.handler;

import lombok.extern.log4j.Log4j2;
import org.rumblekat.club.security.dto.ClubAuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-------------------------------");
        log.info("onAuthenticationSuccess");

        ClubAuthMemberDTO authMemberDTO = (ClubAuthMemberDTO) authentication.getPrincipal();

        boolean fromSocial = authMemberDTO.isFromSocial();
        log.info("Need Modify Member? " + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMemberDTO.getPassword());

        if(fromSocial && passwordResult){
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }
    }
}
/*
* 인증이 성공하거나 실패한 수에 처리를 지정하는 용도
* HttpSecurity 의 FormLogin 이나 oauth2Login 후에는 이러한 핸들러를 이용할수 있다.
*
* /sample/member 주소로 redirect 되는 현상은 Redirect Strategy로 처리 가능
* 일반적인 로그인은 기존과 동일하게 이동하고, 소셜 로그인은 회원 정보를 수정하는 경로로 이동하도록 구현할 수 있다.
*
* RedirectStratgy 인터페이스는 주로 구현 클래스인 DefaultRediectStrategy라는 클래스를 사용해서 처리하는데, 소셜 로그인은 대상 URL을 다르게 지정하는 용도로 사용
*
*
* * */