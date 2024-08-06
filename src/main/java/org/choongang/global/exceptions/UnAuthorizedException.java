package org.choongang.global.exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CommonException {
    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED); // 401
    }

    public UnAuthorizedException() {
        this("Unauthorized");
        setErrorCode(true);
    }
}
