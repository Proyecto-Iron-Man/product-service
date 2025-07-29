package com.ironman.product.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private CategorySummaryResponse category;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
