package org.choongang.file.exceptions;

import org.choongang.global.exceptions.script.AlertException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends AlertException {
    public FileNotFoundException() { //자바 기본 exception도 있지만 다르게 동작하는 걸로 새로 생성
        super("NotFound.file", HttpStatus.NOT_FOUND);
        setErrorCode(true); //코드형태로 출력

    }
}
