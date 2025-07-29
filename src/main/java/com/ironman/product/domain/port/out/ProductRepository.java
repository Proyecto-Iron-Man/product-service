package com.ironman.product.domain.port.out;

import com.ironman.product.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Flux<Product> findAll();

    Mono<Product> findById(Long id);

    Mono<Product> save(Product product);

    Flux<Product> findByQuery(Product product);
}
