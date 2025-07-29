package com.ironman.product.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 300, message = "Name must be between 3 and 300 characters")
    private String name;

    @Size(max = 4000, message = "Description must be less than 4000 characters")
    private String description;

    @PositiveOrZero(message = "Price must be positive")
    private BigDecimal price;

    @PositiveOrZero(message = "Stock must be positive")
    private Integer stock;

    @NotNull(message = "CategoryId is required")
    @Positive(message = "CategoryId must be positive")
    private Long categoryId;
}
