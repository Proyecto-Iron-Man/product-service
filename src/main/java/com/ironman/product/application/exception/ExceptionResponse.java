package com.ironman.product.application.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExceptionResponse {
    private String message;
    private List<ExceptionDetail> details;

    @Getter
    @Builder
    public static class ExceptionDetail {
        private String component;
        private String message;
    }
}
