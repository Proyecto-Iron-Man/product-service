package com.ironman.product.domain.port.in;

import com.ironman.product.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Flux<Product> findAll();
    Mono<Product> findById(Long id);
    Mono<Product> create(Product product);
    Mono<Product> update(Long id, Product product);
    Mono<Product> disable(Long id);
    Flux<Product> findByQuery(Product product);
}
