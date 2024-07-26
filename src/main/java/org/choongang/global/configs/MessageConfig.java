package org.choongang.global.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    //message 프로퍼티 값 가져와서 사용

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8"); //인코딩
        ms.setUseCodeAsDefaultMessage(true); //코드 매핑 값 없을 때 코드값 그대로 출력
        ms.setBasenames("messages.commons", "messages.validations", "messages.errors"); //classpath에서 바로 접근 가능

        return ms;
    }

}
