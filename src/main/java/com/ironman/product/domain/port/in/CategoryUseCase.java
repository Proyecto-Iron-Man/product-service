package com.ironman.product.domain.port.in;

import com.ironman.product.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryUseCase {
    Flux<Category> findAll();

    Mono<Category> findById(Long id);

    Mono<Category> create(Category category);

    Mono<Category> update(Long id, Category category);

    Mono<Category> disable(Long id);
}
