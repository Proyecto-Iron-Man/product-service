package com.ironman.product.infrastructure.persistence.jpa.adapter;

import com.ironman.product.domain.model.Product;
import com.ironman.product.domain.port.out.ProductRepository;
import com.ironman.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import com.ironman.product.infrastructure.persistence.jpa.mapper.ProductJpaEntityMapper;
import com.ironman.product.infrastructure.persistence.jpa.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class ProductJpaRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final ProductJpaEntityMapper productJpaEntityMapper;

    @Override
    public Flux<Product> findAll() {
        return Mono.fromCallable(productJpaRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .map(productJpaEntityMapper::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Product> findById(Long id) {
        return Mono.fromCallable(() -> productJpaRepository.findById(id))
                .flatMap(mapProductMono())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Product> save(Product product) {
        var productEntity = productJpaEntityMapper.toEntity(product);

        return Mono.fromCallable(() -> productJpaRepository.save(productEntity))
                .map(productJpaEntityMapper::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Product> findByQuery(Product product) {
        ProductJpaEntity criteria = productJpaEntityMapper.toEntity(product);

        return Mono.fromCallable(() -> productJpaRepository.findByQuery(criteria))
                .flatMapMany(Flux::fromIterable)
                .map(productJpaEntityMapper::toModel)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Function<Optional<ProductJpaEntity>, Mono<Product>> mapProductMono() {
        return optionalProduct -> optionalProduct
                .map(productJpaEntityMapper::toModel)
                .map(Mono::just)
                .orElseGet(Mono::empty);
    }
}
