package com.ironman.product.application.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApplicationException extends RuntimeException {
    private HttpStatus status;
    private String component;
    private String message;
}
