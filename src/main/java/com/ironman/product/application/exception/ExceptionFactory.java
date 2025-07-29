package com.ironman.product.application.exception;

import com.ironman.product.domain.exception.DomainException;
import org.springframework.http.HttpStatus;

public class ExceptionFactory {
    private ExceptionFactory() {
    }

    public static ApplicationException buildException(DomainException domainException) {
        int statusCode = domainException.getExceptionType().getStatusCode();

        return ApplicationException.builder()
                .status(HttpStatus.valueOf(statusCode))
                .component(domainException.getComponent())
                .message(domainException.getMessage())
                .build();
    }

    public static ApplicationException buildException(Throwable throwable) {
        if (throwable instanceof DomainException domainException) {
            return buildException(domainException);
        }

        return ApplicationExceptionCatalog.UNEXPECTED_ERROR.buildException();
    }
}
