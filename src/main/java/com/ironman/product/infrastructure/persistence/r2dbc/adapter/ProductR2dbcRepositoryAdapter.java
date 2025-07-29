package com.ironman.product.infrastructure.persistence.r2dbc.adapter;

import com.ironman.product.domain.model.Product;
import com.ironman.product.domain.port.out.ProductRepository;
import com.ironman.product.infrastructure.persistence.r2dbc.mapper.ProductR2dbcEntityMapper;
import com.ironman.product.infrastructure.persistence.r2dbc.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class ProductR2dbcRepositoryAdapter implements ProductRepository {
    private final ProductR2dbcRepository productR2dbcRepository;
    private final ProductR2dbcEntityMapper productR2dbcEntityMapper;

    @Override
    public Flux<Product> findAll() {
        return productR2dbcRepository.findAll()
                .map(productR2dbcEntityMapper::toModel);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productR2dbcRepository.findByIdJoinCategory(id)
                .map(productR2dbcEntityMapper::toModel);
    }

    @Override
    public Mono<Product> save(Product product) {
        var productEntity = productR2dbcEntityMapper.toEntity(product);

        return productR2dbcRepository.save(productEntity)
                .map(productR2dbcEntityMapper::toModel);
    }

    @Override
    public Flux<Product> findByQuery(Product product) {
        var productEntity = productR2dbcEntityMapper.toEntity(product);

        return productR2dbcRepository.findByQuery(productEntity)
                .map(productR2dbcEntityMapper::toModel);
    }
}
