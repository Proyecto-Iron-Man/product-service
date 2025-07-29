package com.ironman.product.infrastructure.persistence.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Table("products")
public class ProductR2dbcEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    @Column("category_id")
    private Long categoryId;

    @Transient
    private CategoryR2dbcEntity category;

    private String status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
