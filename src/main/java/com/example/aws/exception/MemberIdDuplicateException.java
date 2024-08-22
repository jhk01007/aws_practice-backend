package com.example.aws.exception;

public class MemberIdDuplicateException extends RuntimeException{
    public MemberIdDuplicateException() {
    }

    public MemberIdDuplicateException(String message) {
        super(message);
    }

    public MemberIdDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberIdDuplicateException(Throwable cause) {
        super(cause);
    }

    public MemberIdDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
