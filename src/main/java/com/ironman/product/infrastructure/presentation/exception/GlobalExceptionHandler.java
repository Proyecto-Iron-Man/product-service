package com.ironman.product.infrastructure.presentation.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ironman.product.application.exception.ApplicationException;
import com.ironman.product.application.exception.ExceptionResponse;
import com.ironman.product.application.exception.ExceptionResponse.ExceptionDetail;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.ironman.product.application.util.ApplicationConstant.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleWebExchangeBindException(WebExchangeBindException ex) {
        List<ExceptionResponse.ExceptionDetail> details = detailsFromBindingResult(ex.getBindingResult());
        ExceptionResponse response = createExceptionResponse(MESSAGE_INVALID_INPUT_DATA, details);

        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleServerWebInputException(ServerWebInputException e) {

        if (e.getCause() instanceof DecodingException decodingException) {
            List<ExceptionResponse.ExceptionDetail> details = List.of();

            if (decodingException.getCause() instanceof MismatchedInputException mismatchedInputException) {
                details = detailsFromMismatchedInputException(mismatchedInputException);
            }

            ExceptionResponse response = createExceptionResponse(MESSAGE_INVALID_INPUT_DATA, details);
            return Mono.just(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response));
        }

        ExceptionResponse response = createExceptionResponse(MESSAGE_UNEXPECTED_ERROR, List.of());
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response));
    }

    @ExceptionHandler(ApplicationException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleFunctionalException(ApplicationException e) {
        String message = messageFromStatus(e.getStatus());
        List<ExceptionResponse.ExceptionDetail> details = detailsFromFunctionalException(e);
        ExceptionResponse response = createExceptionResponse(message, details);

        return Mono.just(ResponseEntity
                .status(e.getStatus())
                .body(response));
    }

    private static ExceptionResponse createExceptionResponse(String message, List<ExceptionDetail> details) {
        return ExceptionResponse.builder()
                .message(message)
                .details(details)
                .build();
    }

    private static String messageFromStatus(HttpStatus status) {
        return switch (status) {
            case NOT_FOUND -> MESSAGE_NOT_FOUND;
            case BAD_REQUEST -> MESSAGE_INVALID_INPUT_DATA;
            case INTERNAL_SERVER_ERROR -> MESSAGE_INTERNAL_ERROR;
            default -> MESSAGE_UNEXPECTED_ERROR;
        };
    }

    private static String buildFieldName(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .filter(name -> name != null && !name.isEmpty())
                .reduce((a, b) -> a + "." + b)
                .orElse("unknown");
    }

    private static List<ExceptionResponse.ExceptionDetail> detailsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> ExceptionResponse.ExceptionDetail.builder()
                        .component(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();
    }

    private static List<ExceptionResponse.ExceptionDetail> detailsFromMismatchedInputException(
            MismatchedInputException mismatchedInputException) {
        String propertyName = mismatchedInputException.getTargetType().getSimpleName();

        String fieldName = buildFieldName(mismatchedInputException.getPath());

        String detailMessage = switch (propertyName) {
            case DATA_TYPE_INTEGER -> MESSAGE_INVALID_INTEGER;
            case DATA_TYPE_LONG -> MESSAGE_INVALID_LONG;
            default -> MESSAGE_INVALID_VALUE;
        };

        return List.of(ExceptionResponse.ExceptionDetail.builder()
                .component(fieldName)
                .message(detailMessage)
                .build());
    }

    private static List<ExceptionResponse.ExceptionDetail> detailsFromFunctionalException(
            ApplicationException functionalException) {
        return List.of(ExceptionResponse.ExceptionDetail.builder()
                .component(functionalException.getComponent())
                .message(functionalException.getMessage())
                .build());
    }
}
