package com.ironman.product.infrastructure.persistence.r2dbc.repository;

import com.ironman.product.infrastructure.persistence.r2dbc.entity.ProductR2dbcEntity;
import com.ironman.product.infrastructure.persistence.r2dbc.repository.custom.ProductCustomRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductR2dbcEntity, Long>, ProductCustomRepository {
}
