package com.ironman.product.application.mapper;

import com.ironman.product.application.dto.*;
import com.ironman.product.domain.model.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
        componentModel = ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CategoryDtoMapper.class}
)
public interface ProductDtoMapper {
    ProductSummaryResponse toSummaryResponse(Product product);

    ProductDetailResponse toDetailResponse(Product product);

    ProductOverviewResponse toOverviewResponse(Product product);

    ProductResponse toResponse(Product product);

    Product toModel(ProductRequest productRequest);

    Product toModelQuery(ProductQueryRequest productQueryRequest);
}
