package org.choongang.global.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class CommonException extends RuntimeException {
    //우리가 따로 예외처리를 해야하는 경우 우리가 정의한 예외는 해당 클래스에서 나옴

    private boolean errorCode;
    private HttpStatus status; //응답코드에 맞게 예외 내보낼 것
    private Map<String, List<String>> errorMessages;

    public CommonException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR); //기본 응답 코드는 500
    }

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
