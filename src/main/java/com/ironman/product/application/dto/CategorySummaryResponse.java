package com.ironman.product.application.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryResponse {
    private Long id;
    private String name;
    private String urlKey;
}
