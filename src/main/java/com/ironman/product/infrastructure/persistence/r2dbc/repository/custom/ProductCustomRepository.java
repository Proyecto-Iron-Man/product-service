package com.ironman.product.infrastructure.persistence.r2dbc.repository.custom;

import com.ironman.product.infrastructure.persistence.r2dbc.entity.ProductR2dbcEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCustomRepository {
    Mono<ProductR2dbcEntity> findByIdJoinCategory(Long id);

    Flux<ProductR2dbcEntity> findByQuery(ProductR2dbcEntity product);
}
