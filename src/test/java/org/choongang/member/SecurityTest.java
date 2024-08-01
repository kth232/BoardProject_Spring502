package org.choongang.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //get, post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc //mock을 사용한 테스트 시 추가 필요
@ActiveProfiles("test")
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("mock을 사용한 로그인 테스트_응답코드 확인")
    void test01() throws Exception {
        mockMvc.perform(post("/member/join")
                        .with(csrf().asHeader()) //토큰을 헤더에 넣어주면 응답코드 200, 안넣어주면 403
                .param("email", "user02@test.org"))
                .andDo(print());
    }

    @Test
    @WithMockUser //가짜 유저 데이터
    @DisplayName("비회원 테스트_응답코드 확인")
    void test02() throws Exception {
        mockMvc.perform(get("/mypage"))
                .andDo(print());
        //로그인 상태가 아니기 때문에 302 응답코드, 다른 유저가 mypage로 들어갈 때 404
    }

    @Test
    @WithMockUser(username = "user05", authorities = "ADMIN") //가짜 유저 데이터
    @DisplayName("일반회원 테스트_응답코드 확인")
    void test03() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print());
        //관리자가 아니라면 402 응답코드, 관리자라면 200
    }
}
