package com.ironman.product.application.service;

import com.ironman.product.application.dto.CategoryDetailResponse;
import com.ironman.product.application.dto.CategoryRequest;
import com.ironman.product.application.dto.CategoryResponse;
import com.ironman.product.application.dto.CategorySummaryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {
    Flux<CategorySummaryResponse> findAll();

    Mono<CategoryDetailResponse> findById(Long id);

    Mono<CategoryResponse> create(CategoryRequest categoryRequest);

    Mono<CategoryResponse> update(Long id, CategoryRequest categoryRequest);

    Mono<CategoryResponse> disable(Long id);
}
