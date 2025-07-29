package com.ironman.product.application.adapter;

import com.ironman.product.application.dto.CategoryDetailResponse;
import com.ironman.product.application.dto.CategoryRequest;
import com.ironman.product.application.dto.CategoryResponse;
import com.ironman.product.application.dto.CategorySummaryResponse;
import com.ironman.product.application.exception.ExceptionFactory;
import com.ironman.product.application.mapper.CategoryDtoMapper;
import com.ironman.product.application.service.CategoryService;
import com.ironman.product.domain.model.Category;
import com.ironman.product.domain.port.in.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceAdapter implements CategoryService {
    private final CategoryUseCase categoryUseCase;
    private final CategoryDtoMapper categoryDtoMapper;


    @Override
    public Flux<CategorySummaryResponse> findAll() {
        return categoryUseCase.findAll()
                .map(categoryDtoMapper::toSummaryResponse)
                .onErrorResume(throwable -> {
                    log.error("Error finding all categories", throwable);
                    return Flux.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<CategoryDetailResponse> findById(Long id) {
        return categoryUseCase.findById(id)
                .map(categoryDtoMapper::toDetailResponse)
                .onErrorResume(throwable -> {
                    log.error("Error finding category by id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });

    }

    @Override
    public Mono<CategoryResponse> create(CategoryRequest categoryRequest) {
        Category category = categoryDtoMapper.toModel(categoryRequest);

        return categoryUseCase.create(category)
                .map(categoryDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error creating category", throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<CategoryResponse> update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryDtoMapper.toModel(categoryRequest);

        return categoryUseCase.update(id, category)
                .map(categoryDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error updating category with id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<CategoryResponse> disable(Long id) {
        return categoryUseCase.disable(id)
                .map(categoryDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error disabling category with id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }
}
