package com.ironman.product.infrastructure.persistence.jpa.mapper;

import com.ironman.product.domain.model.Category;
import com.ironman.product.infrastructure.persistence.jpa.entity.CategoryJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryJpaEntityMapper {


    Category toModel(CategoryJpaEntity categoryJpaEntity);

    CategoryJpaEntity toEntity(Category category);
}
