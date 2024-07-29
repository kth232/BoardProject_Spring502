package org.choongang.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter; //패키지 잘 확인하기
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing //jpa에서 엔티티 변경 이력 관리 기능 활성화
public class MvcConfig implements WebMvcConfigurer { //기본 설정


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        //css 파일 인식 못할 때 정적 변수로 명시해주면 됨
    }

    /**
     * <input type="hidden" name="_method" value="PATCH"> ->PATCH 방식으로 요청
     * ?_method=DELETE
     * @return
     */
    @Bean //빈으로 등록해야 동작함!
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter(); //post방식으로 들어와도 patch 방식으로
    }
}
