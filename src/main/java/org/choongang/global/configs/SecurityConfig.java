package org.choongang.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig { //시큐리티 설정
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //시큐리티 설정 무력화->기본 로그인 화면 나오지 않음
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //인코딩 방식을 알아야 해줌, encode, matches 메서드를 주로 사용
        return new BCryptPasswordEncoder(); //인코딩, 내부에서 bcrypt로 인증 처리
    }
}
