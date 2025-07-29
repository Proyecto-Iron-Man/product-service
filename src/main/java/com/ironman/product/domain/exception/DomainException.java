package com.ironman.product.domain.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class DomainException extends RuntimeException {
    private ExceptionType exceptionType;
    private String component;
    private String message;

    @RequiredArgsConstructor
    @Getter
    public enum ExceptionType {
        NOT_FOUND(404),
        INTERNAL_SERVER_ERROR(500);

        private final int statusCode;
    }

}
