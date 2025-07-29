package com.ironman.product.domain.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    ENABLED("A") {
        @Override
        public String toString() {
            return "Habilitado";
        }
    },
    DISABLED("E") {
        @Override
        public String toString() {
            return "Deshabilitado";
        }
    };

    private final String code;
    private final String name;

    StatusEnum(String code) {
        this.code = code;
        this.name = this.toString();
    }
}
