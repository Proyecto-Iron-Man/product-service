package com.ironman.product.infrastructure.persistence.jpa.repository;

import com.ironman.product.infrastructure.persistence.jpa.entity.CategoryJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface CategoryJpaRepository extends ListCrudRepository<CategoryJpaEntity, Long> {
}
