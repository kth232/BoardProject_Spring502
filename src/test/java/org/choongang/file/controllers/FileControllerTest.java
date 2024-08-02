package org.choongang.file.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc //mock mvc 자동 설정
@SpringBootTest
@ActiveProfiles("test")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockMultipartFile file1;
    private MockMultipartFile file2;

    @BeforeEach
    void init() {
        file1 = new MockMultipartFile("file", "test01.png", "images/png", "ABC".getBytes());
        file2 = new MockMultipartFile("file", "test02.png", "images/png", "DEF".getBytes());
    }

    @Test
    @DisplayName("파일 업로드 테스트")
    void uploadTest() throws Exception {
        mockMvc.perform(multipart("/file/upload")
                .file(file1)
                .file(file2)
                .param("gid", "testgid")
                .param("location", "testlocation")
                        .with(csrf().asHeader()) //토큰 실어보내야 함, 아니면 오류 발생
                ).andDo(print()).andExpect(status().isCreated());
    }
}
