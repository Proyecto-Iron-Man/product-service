package com.ironman.product.infrastructure.persistence.r2dbc.adapter;

import com.ironman.product.domain.model.Category;
import com.ironman.product.domain.port.out.CategoryRepository;
import com.ironman.product.infrastructure.persistence.r2dbc.entity.CategoryR2dbcEntity;
import com.ironman.product.infrastructure.persistence.r2dbc.mapper.CategoryR2dbcEntityMapper;
import com.ironman.product.infrastructure.persistence.r2dbc.repository.CategoryR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CategoryR2dbcRepositoryAdapter implements CategoryRepository {
    private final CategoryR2dbcRepository categoryR2dbcRepository;
    private final CategoryR2dbcEntityMapper categoryR2dbcEntityMapper;

    @Override
    public Flux<Category> findAll() {
        return categoryR2dbcRepository.findAll()
                .map(categoryR2dbcEntityMapper::toModel);
    }

    @Override
    public Mono<Category> findById(Long id) {
        return categoryR2dbcRepository.findById(id)
                .map(categoryR2dbcEntityMapper::toModel);
    }

    @Override
    public Mono<Category> save(Category category) {
        CategoryR2dbcEntity categoryEntity = categoryR2dbcEntityMapper.toEntity(category);

        return categoryR2dbcRepository.save(categoryEntity)
                .map(categoryR2dbcEntityMapper::toModel);
    }
}
