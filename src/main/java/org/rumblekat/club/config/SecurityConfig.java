package org.rumblekat.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/sample/all").permitAll()
                .antMatchers("/sample/member").hasRole("USER");
        http.formLogin();
        http.csrf().disable(); //rest 방식으로 이용할수 있도록 보안설정을 다루기 위해 csrf 토큰을 발행하지 않는다.
        http.logout();
        /*
            csrf 토큰 방식을 사용하면, GET 방식으로도 처리 가능
            POST 방식으로만 로그아웃을 해야됨
            invalidatedHttpSession, deleteCookies 를 이용해서 쿠키나 세션을 무효화시키는 설정도 가능
        */
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //사용자 계정은 user1
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder().encode("1111"))
                .roles("USER");
    }
}
