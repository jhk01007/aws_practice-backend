package com.example.aws.exception;


public class S3ImageUploadException extends RuntimeException{
    public S3ImageUploadException() {

    }

    public S3ImageUploadException(String message) {
        super(message);
    }

    public S3ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3ImageUploadException(Throwable cause) {
        super(cause);
    }

    public S3ImageUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
