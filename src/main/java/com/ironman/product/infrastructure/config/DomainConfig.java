package com.ironman.product.infrastructure.config;

import com.ironman.product.domain.port.in.CategoryUseCase;
import com.ironman.product.domain.port.in.ProductUseCase;
import com.ironman.product.domain.port.out.CategoryRepository;
import com.ironman.product.domain.port.out.ProductRepository;
import com.ironman.product.domain.usecase.CategoryUseCaseImpl;
import com.ironman.product.domain.usecase.ProductUseCaseImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public CategoryUseCase categoryUseCase(
            @Qualifier("categoryR2dbcRepositoryAdapter") CategoryRepository categoryRepository
    ) {
        return new CategoryUseCaseImpl(categoryRepository);
    }

    @Bean
    public ProductUseCase productUseCase(
            @Qualifier("categoryJpaRepositoryAdapter") CategoryRepository categoryRepository,
            @Qualifier("productJpaRepositoryAdapter") ProductRepository productRepository
    ) {
        return new ProductUseCaseImpl(categoryRepository, productRepository);

    }
}
