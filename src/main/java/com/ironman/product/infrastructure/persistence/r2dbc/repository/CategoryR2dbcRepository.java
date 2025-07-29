package com.ironman.product.infrastructure.persistence.r2dbc.repository;

import com.ironman.product.infrastructure.persistence.r2dbc.entity.CategoryR2dbcEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryR2dbcRepository extends ReactiveCrudRepository<CategoryR2dbcEntity, Long> {
}
