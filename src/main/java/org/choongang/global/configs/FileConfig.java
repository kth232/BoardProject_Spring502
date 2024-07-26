package org.choongang.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class) //FileProperties 클래스에 정의한 값 주입받아서 사용 가능
public class FileConfig implements WebMvcConfigurer {
    //프로퍼티 값을 데이터 클래스로 분리해서 자동 주입 가능하게 만들 수 있음<-스프링이 제공하는 기능 중 하나

    private final FileProperties properties;

    /*
    //설정 파일에 작성한 변수명으로 값 대입
    @Value("${file.upload.path}")
    private String path;

    @Value("${file.upload.path}")
    private String url;
    */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //외부 정적 자원 경로, 위치를 매핑하는 메서드
        // /upload/**
        registry.addResourceHandler(properties.getUrl() + "**")
                .addResourceLocations("file:///" + properties.getPath());

    }
}
