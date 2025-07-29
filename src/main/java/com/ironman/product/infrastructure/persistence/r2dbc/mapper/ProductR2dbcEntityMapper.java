package com.ironman.product.infrastructure.persistence.r2dbc.mapper;


import com.ironman.product.domain.model.Product;
import com.ironman.product.infrastructure.persistence.r2dbc.entity.ProductR2dbcEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
        componentModel = ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CategoryR2dbcEntityMapper.class}
)
public interface ProductR2dbcEntityMapper {
    Product toModel(ProductR2dbcEntity productEntity);

    ProductR2dbcEntity toEntity(Product product);
}
