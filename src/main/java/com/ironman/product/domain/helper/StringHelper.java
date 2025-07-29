package com.ironman.product.domain.helper;

import java.text.Normalizer;

import static java.util.Optional.ofNullable;

public class StringHelper {
    private StringHelper() {
    }

    public static String normalize(String input) {
        return ofNullable(input)
                .map(value -> Normalizer.normalize(value, Normalizer.Form.NFD))
                .orElse(null);
    }

    public static String removeAccents(String input) {
        return ofNullable(input)
                .map(StringHelper::normalize)
                .map(value -> value.replaceAll("\\p{M}", ""))
                .orElse(null);
    }

    public static String removePunctuation(String input) {
        return ofNullable(input)
                .map(StringHelper::normalize)
                .map(value -> value.replaceAll("\\p{Punct}", ""))
                .orElse(null);
    }

    public static String replaceWhitespace(String input, String replacement) {
        return ofNullable(input)
                .map(StringHelper::normalize)
                .map(value -> value.replaceAll("\\s+", replacement))
                .orElse(null);
    }
}
