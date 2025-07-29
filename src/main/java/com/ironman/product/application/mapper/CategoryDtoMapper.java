package com.ironman.product.application.mapper;

import com.ironman.product.application.dto.*;
import com.ironman.product.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryDtoMapper {
    CategorySummaryResponse toSummaryResponse(Category category);

    CategoryDetailResponse toDetailResponse(Category category);

    CategoryMinimalResponse toMinimalResponse(Category category);

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toModel(CategoryRequest request);
}
