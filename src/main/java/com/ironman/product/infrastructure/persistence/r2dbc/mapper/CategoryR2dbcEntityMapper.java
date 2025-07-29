package com.ironman.product.infrastructure.persistence.r2dbc.mapper;

import com.ironman.product.domain.model.Category;
import com.ironman.product.infrastructure.persistence.r2dbc.entity.CategoryR2dbcEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryR2dbcEntityMapper {

    Category toModel(CategoryR2dbcEntity categoryEntity);

    CategoryR2dbcEntity toEntity(Category category);
}
