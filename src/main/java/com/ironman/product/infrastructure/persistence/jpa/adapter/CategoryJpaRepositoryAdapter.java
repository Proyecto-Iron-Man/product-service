package com.ironman.product.infrastructure.persistence.jpa.adapter;

import com.ironman.product.domain.model.Category;
import com.ironman.product.domain.port.out.CategoryRepository;
import com.ironman.product.infrastructure.persistence.jpa.entity.CategoryJpaEntity;
import com.ironman.product.infrastructure.persistence.jpa.mapper.CategoryJpaEntityMapper;
import com.ironman.product.infrastructure.persistence.jpa.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class CategoryJpaRepositoryAdapter implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryJpaEntityMapper categoryJpaEntityMapper;


    @Override
    public Flux<Category> findAll() {
        return Mono.fromCallable(categoryJpaRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .map(categoryJpaEntityMapper::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Category> findById(Long id) {
        return Mono.fromCallable(() -> categoryJpaRepository.findById(id))
                .flatMap(mapCategoryMono())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Category> save(Category category) {
        var categoryEntity = categoryJpaEntityMapper.toEntity(category);

        return Mono.fromCallable(() -> categoryJpaRepository.save(categoryEntity))
                .map(categoryJpaEntityMapper::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Function<Optional<CategoryJpaEntity>, Mono<Category>> mapCategoryMono() {
        return optionalCategory -> optionalCategory
                .map(categoryJpaEntityMapper::toModel)
                .map(Mono::just)
                .orElseGet(Mono::empty);
    }
}
