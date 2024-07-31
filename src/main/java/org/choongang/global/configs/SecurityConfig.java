package org.choongang.global.configs;

import org.choongang.member.services.LoginFailureHandler;
import org.choongang.member.services.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig { //시큐리티 설정

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //시큐리티 설정 무력화->기본 로그인 화면 나오지 않음

        //로그인, 로그아웃 설정 S
        //도메인 별로(도메인 특화), 람다식으로 설정, 애매한 부분들은 직접 설정 가능<-3버전 이상부터
        //옛날엔 메서드 체이닝 방식 사용
        http.formLogin(f -> {
            //이메일 항목으로 회원명 넘겨줌
            f.loginPage("/member/login") //로그인 페이지를 찾아서 인증 처리해줌
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler()); //상세한 로그인 성공, 실패 처리
//                    .successForwardUrl("/") //성공 시 메인 페이지로 이동
//                    .failureForwardUrl("/member/login?error=true"); //실패 시 로그인 페이지, 에러 트루 메세지 url에 매핑
        });

        http.logout(f -> {
            f.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃할 주소
                    .logoutSuccessUrl("/member/login"); //로그아웃 성공 시 이동할 주소
        });
        //로그인 처리로 페이지 이동하는 실제로 잘 안함->상세 처리를 위해 핸들러 이용
        //로그인, 로그아웃 E
        
        //인가(접근 통제) 설정 S
        //도메인 별로(도메인 특화), 람다식으로 설정, 애매한 부분들은 직접 설정 가능<-3버전 이상부터
        http.authorizeRequests(c -> {
            //mypage 포함한 하위 전체 경로
            c.requestMatchers("/mypage/**").authenticated() //일부 페이지만 회원 전용
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") //hasAuthority: 관리자 권한 다양하게 줄 수 있음
                    .anyRequest().permitAll();
        });
        //인가(접근 통제) 설정 E

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //인코딩 방식을 알아야 해줌, encode, matches 메서드를 주로 사용
        return new BCryptPasswordEncoder(); //인코딩, 내부에서 bcrypt로 인증 처리
    }
}
