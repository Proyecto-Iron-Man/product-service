package com.ironman.product.application.service;

import com.ironman.product.application.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<ProductSummaryResponse> findAll();
    Mono<ProductDetailResponse> findById(Long id);
    Mono<ProductResponse> create(ProductRequest productRequest);
    Mono<ProductResponse> update(Long id, ProductRequest productRequest);
    Mono<ProductResponse> disable(Long id);
    Flux<ProductOverviewResponse> findByQuery(ProductQueryRequest productQueryRequest);
}
