package com.ironman.product.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static com.ironman.product.application.util.ApplicationConstant.COMPONENT_NAME;

@Getter
@RequiredArgsConstructor
public enum ApplicationExceptionCatalog {
    UNEXPECTED_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            COMPONENT_NAME,
            "An unexpected error occurred during the process."
    );


    private final HttpStatus status;
    private final String component;
    private final String message;

    public ApplicationException buildException() {
        return ApplicationException.builder()
                .status(this.status)
                .component(this.component)
                .message(this.message)
                .build();
    }
}
