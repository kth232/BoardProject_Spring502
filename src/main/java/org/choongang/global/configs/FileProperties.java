package org.choongang.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="file.upload") //설정 파일에서 정의한 값 주입
public class FileProperties { //관련 설정들을 모아서 정리한 클래스->어떤 설정인지 파악하기 편함
    private String path; //file.upload.path
    private String url; //file.upload.url
}
