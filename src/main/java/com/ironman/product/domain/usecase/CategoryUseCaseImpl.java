package com.ironman.product.domain.usecase;

import com.ironman.product.domain.enums.StatusEnum;
import com.ironman.product.domain.exception.DomainExceptionCatalog;
import com.ironman.product.domain.helper.StringHelper;
import com.ironman.product.domain.model.Category;
import com.ironman.product.domain.port.in.CategoryUseCase;
import com.ironman.product.domain.port.out.CategoryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.ironman.product.domain.helper.DomainConstant.REPLACEMENT_CHARACTER;
import static java.util.Optional.ofNullable;

//@Service
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements CategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> findById(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(notFoundCategory(id));
    }

    @Override
    public Mono<Category> create(Category category) {
        category.setStatus(StatusEnum.ENABLED.getCode());
        category.setCreatedAt(LocalDateTime.now());
        category.setUrlKey(generateUrlKey(category.getName()));

        return categoryRepository.save(category);
    }

    @Override
    public Mono<Category> update(Long id, Category category) {
        category.setUrlKey(generateUrlKey(category.getName()));

        return categoryRepository.findById(id)
                .switchIfEmpty(notFoundCategory(id))
                .map(categoryFound -> {
                    categoryFound.setName(category.getName());
                    categoryFound.setDescription(category.getDescription());
                    categoryFound.setUrlKey(category.getUrlKey());
                    // BeanUtils.copyProperties(categoryFound, category);

                    categoryFound.setUpdatedAt(LocalDateTime.now());
                    return categoryFound;
                })
                .flatMap(categoryRepository::save);
    }

    @Override
    public Mono<Category> disable(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(notFoundCategory(id))
                .map(category -> {
                    category.setStatus(StatusEnum.DISABLED.getCode());
                    category.setUpdatedAt(LocalDateTime.now());
                    return category;
                })
                .flatMap(categoryRepository::save);
    }

    private static Mono<Category> notFoundCategory(Long id) {
        return Mono.error(DomainExceptionCatalog.ENTITY_NOT_FOUND_BY_ID.buildException("Category", id));
    }

    private String generateUrlKey(String input) {
        return ofNullable(input)
                .map(StringHelper::removeAccents)
                .map(StringHelper::removePunctuation)
                .map(value -> StringHelper.replaceWhitespace(value, REPLACEMENT_CHARACTER))
                .map(String::toLowerCase)
                .orElse(input);
    }

}
