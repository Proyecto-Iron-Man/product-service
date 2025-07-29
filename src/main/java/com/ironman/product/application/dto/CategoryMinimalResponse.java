package com.ironman.product.application.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMinimalResponse {
    private Long id;
    private String name;
}
