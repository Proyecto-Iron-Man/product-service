package com.ironman.product.infrastructure.persistence.jpa.entity.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProductProjection {
    Long getId();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    Integer getStock();
    String getStatus();
    LocalDateTime getCreatedAt();
    Long getCategoryId();
    String getCategoryName();
}
