package org.choongang.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("utils")
@RequiredArgsConstructor
public class Utils { // 빈의 이름 - utils

    private final MessageSource messageSource; //메세지 소스 빈 사용
    private final HttpServletRequest request; //locale 정보 가져오기 위함
    private final DiscoveryClient discoveryClient;

    public String url(String url) {
        List<ServiceInstance> instances = discoveryClient.getInstances("front-service");

        try {
            return String.format("%s%s", instances.get(0).getUri().toString(), url);
        } catch (Exception e) {
            return String.format("%s://%s:%d%s%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), url);
        }
    }

    public Map<String, List<String>> getErrorMessages(Errors errors) {
        //맵 형태로 필드, 에러메세지 저장,메세지는 필드마다 여러개 나올 수 있기 때문에 리스트형으로 지정

        //FieldErrors
        Map<String, List<String>> messages = errors.getFieldErrors() //error 코드 정보를 string으로 가져와서 map형태로 만듦
                .stream() //스트림 잘 활용하면 코드 길이 축소
                .collect(Collectors.toMap(FieldError::getField, e -> getCodeMessages(e.getCodes()))); //맵 형태로 데이터 모아줌, 메서드 참조
        //키에는 필드명, 값에는 메세지 코드
        //getCodes()가 가져오는 코드= resource#application.properties에 작성한 코드들, 여러개가 나올 수 있기 때문에 배열형태

        //GlobalErrors
        List<String> gMessages = errors.getGlobalErrors()
                .stream()
                .flatMap(e->getCodeMessages(e.getCodes()).stream()).toList();
        //1차원적인 플랫맵 형태

        if(!gMessages.isEmpty()) { //global메세지가 항상 있는 것은 아님
            messages.put("global", gMessages); //gMessage가 있을 때만
        }
        return messages;
    }

    //반복되는 코드는 함수로 분리
    public List<String> getCodeMessages(String[] codes) { //코드 배열을 메세지로 일괄적으로 바꿔줌

        ResourceBundleMessageSource ms = (ResourceBundleMessageSource) messageSource;
        ms.setUseCodeAsDefaultMessage(false); //현재 코드가 있는 것만 나오고 없으면 예외 발생하도록
        //코드 그 자체로 메세지 대체하도록 설정해 놓은 것을 false로 돌림->예외 발생

        List<String> messages = Arrays.stream(codes)
                .map(c-> {
                    try {
                        //getMessage의 인자: code, 치환되는 부분은 메세지에서는 필요없기 때문에 null(원래는 Object 배열형), 지역화를 위해 브라우저 locale 정보 가져옴(언어설정이 영어이면 영어로)
                        return ms.getMessage(c, null, request.getLocale());
                    } catch (Exception e) {
                        return ""; //비어있는 메세지는 안나옴
                    }
                })
                .filter(s -> !s.isBlank()) //s != null &&, null이 아니고 / blank가 아닐 때
                .toList(); //리스트 형태로 만듦

        ms.setUseCodeAsDefaultMessage(true); //싱글톤으로 관리하기 때문에 사용하면 다시 원래 상태로 돌려줌
        return messages;
    }

    public String getMessage(String code) { //기존 함수를 활용하여 코드를 통해 메세지 출력하는 함수 추가
        List<String> messages = getCodeMessages(new String[] {code});
        //없으면 코드 반환, 있으면 메세지 반환
        return messages.isEmpty() ? "code" : messages.get(0);
    }
}
