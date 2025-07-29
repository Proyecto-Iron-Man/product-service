package com.ironman.product.application.adapter;

import com.ironman.product.application.dto.*;
import com.ironman.product.application.exception.ApplicationExceptionCatalog;
import com.ironman.product.application.exception.ExceptionFactory;
import com.ironman.product.application.mapper.ProductDtoMapper;
import com.ironman.product.application.service.ProductService;
import com.ironman.product.domain.exception.DomainException;
import com.ironman.product.domain.port.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductService {
    private final ProductUseCase productUseCase;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public Flux<ProductSummaryResponse> findAll() {
        return productUseCase.findAll()
                .map(productDtoMapper::toSummaryResponse)
                .onErrorResume(throwable -> {
                    log.error("Error finding all products", throwable);
                    return Flux.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<ProductDetailResponse> findById(Long id) {
        return productUseCase.findById(id)
                .map(productDtoMapper::toDetailResponse)
                .onErrorResume(throwable -> {
                    log.error("Error finding product by id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<ProductResponse> create(ProductRequest productRequest) {
        return productUseCase.create(productDtoMapper.toModel(productRequest))
                .map(productDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error creating product", throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<ProductResponse> update(Long id, ProductRequest productRequest) {
        return productUseCase.update(id, productDtoMapper.toModel(productRequest))
                .map(productDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error updating product with id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Mono<ProductResponse> disable(Long id) {
        return productUseCase.disable(id)
                .map(productDtoMapper::toResponse)
                .onErrorResume(throwable -> {
                    log.error("Error disabling product with id: {}", id, throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }

    @Override
    public Flux<ProductOverviewResponse> findByQuery(ProductQueryRequest productQueryRequest) {
        var product = productDtoMapper.toModelQuery(productQueryRequest);

        return productUseCase.findByQuery(product)
                .map(productDtoMapper::toOverviewResponse)
                .onErrorResume(throwable -> {
                    log.error("Error finding products by query", throwable);
                    return Mono.error(ExceptionFactory.buildException(throwable));
                });
    }
}
