package com.ironman.product.application.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryRequest {
    private String name;
    private Integer stock;
    private Long categoryId;
}
