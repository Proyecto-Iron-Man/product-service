package com.ironman.product.domain.port.out;

import com.ironman.product.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository {
    Flux<Category> findAll();

    Mono<Category> findById(Long id);

    Mono<Category> save(Category category);
}
