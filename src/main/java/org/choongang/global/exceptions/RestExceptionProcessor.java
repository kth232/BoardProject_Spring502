package org.choongang.global.exceptions;

import org.choongang.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

//@RestControllerAdvice("org.choongang")
public interface RestExceptionProcessor {

    @ExceptionHandler(Exception.class)
    default ResponseEntity<JSONData> errorHandler(Exception e) { //공통적인 에러 처리 정의
        //반환값 = responseEntity->상세한 응답코드 정보 조회 위함

        Object message = e.getMessage(); //일반 메세지 가져옴

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500, 기본 에러
        if (e instanceof CommonException commonException) { //개발자 정의 에러, commonException 하위 클래스로 정의
            status = commonException.getStatus(); //상태 코드 가져오기

            Map<String, List<String>> errorMessages = commonException.getErrorMessages();
            if(errorMessages != null) message = errorMessages;
            //커맨드 객체 검증에서 던져진 예외, null이면 일반 메세지
        }

        //JSON 형태로 통일성 있게 출력, 커맨드 객체 검증
        JSONData data = new JSONData();
        data.setSuccess(false);
        data.setMessage(message); //원래는 문자열만 출력이 가능함->obj형 메세지 객체로 대체
        data.setStatus(status);

        e.printStackTrace();

        return ResponseEntity.status(status).body(data); //응답 코드 직접 넣음
    }
}
