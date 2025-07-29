package com.ironman.product.domain.usecase;

import com.ironman.product.domain.enums.StatusEnum;
import com.ironman.product.domain.exception.DomainExceptionCatalog;
import com.ironman.product.domain.model.Category;
import com.ironman.product.domain.model.Product;
import com.ironman.product.domain.port.in.ProductUseCase;
import com.ironman.product.domain.port.out.CategoryRepository;
import com.ironman.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

//@Service
@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> create(Product product) {
        product.setStatus(StatusEnum.ENABLED.getCode());
        product.setCreatedAt(LocalDateTime.now());

        return categoryRepository.findById(product.getCategoryId())
                .switchIfEmpty(notFoundCategory(product))
                .flatMap(category -> productRepository.save(product));
    }

    @Override
    public Mono<Product> update(Long id, Product product) {

        return categoryRepository.findById(product.getCategoryId())
                .switchIfEmpty(notFoundCategory(product))
                .flatMap(category -> productRepository.findById(id))
                .switchIfEmpty(notFoundProduct(id))
                .map(productFound -> {
                    BeanUtils.copyProperties(productFound, product);

                    productFound.setUpdatedAt(LocalDateTime.now());
                    return productFound;
                })
                .flatMap(productRepository::save);
    }

    @Override
    public Mono<Product> disable(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(notFoundProduct(id))
                .map(productFound -> {
                    productFound.setStatus(StatusEnum.DISABLED.getCode());
                    return productFound;
                })
                .flatMap(productRepository::save);
    }

    @Override
    public Flux<Product> findByQuery(Product product) {
        return productRepository.findByQuery(product)
                .switchIfEmpty(productsNotFoundByCriteria());
    }

    private static Flux<Product> productsNotFoundByCriteria() {
        return Flux.error(DomainExceptionCatalog.NO_RESULTS_FOR_CRITERIA.buildException("Products"));
    }

    private static Mono<Product> notFoundProduct(Long id) {
        return Mono.error(DomainExceptionCatalog.ENTITY_NOT_FOUND_BY_ID.buildException("Product", id));
    }

    private static Mono<Category> notFoundCategory(Product product) {
        return Mono.error(DomainExceptionCatalog.ENTITY_NOT_FOUND_BY_ID
                .buildException("Category", product.getCategoryId()));
    }
}
