package org.choongang.file.services;

import org.choongang.file.entities.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class FileUploadServiceTest {

    @Autowired
    private FileUploadService uploadService;
    @Autowired
    private RestClientAutoConfiguration restClientAutoConfiguration;

    @Test
    void uploadTest() {
        MockMultipartFile file1 = new MockMultipartFile("file", "text1.png", "images/png", "ABC".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "text2.png", "images/png", "ABC".getBytes());

        List<FileInfo> items = uploadService.upload(new MultipartFile[] {file1, file2}, "testgid", "testlocation");

        items.forEach(System.out::println);
        //시간이 걸리더라도 테스트 진행해보기!
        //완벽한 테스트는 아니지만 간단한 실수는 방지할 수 있다
    }
}
