package com.ironman.product.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String urlKey;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
