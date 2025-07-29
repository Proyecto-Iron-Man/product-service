package com.ironman.product.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.ironman.product.domain.helper.DomainConstant.DATA_SERVICE_COMPONENT;

@Getter
@RequiredArgsConstructor
public enum DomainExceptionCatalog {

    ENTITY_NOT_FOUND_BY_ID(
            DomainException.ExceptionType.NOT_FOUND,
            DATA_SERVICE_COMPONENT,
            "No se encontró %s con ID: %s"
    ),
    NO_RESULTS_FOR_CRITERIA(
            DomainException.ExceptionType.NOT_FOUND,
            DATA_SERVICE_COMPONENT,
            "No se encontraron %s que coincidan con los criterios especificados"
    );

    private final DomainException.ExceptionType exceptionType;
    private final String component;
    private final String message;


    public DomainException buildException(Object... args) {
        String formattedMessage = String.format(message, args);
        return DomainException.builder()
                .exceptionType(exceptionType)
                .component(component)
                .message(formattedMessage)
                .build();
    }
}
