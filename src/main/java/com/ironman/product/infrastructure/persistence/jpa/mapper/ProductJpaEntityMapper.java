package com.ironman.product.infrastructure.persistence.jpa.mapper;

import com.ironman.product.domain.model.Product;
import com.ironman.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import com.ironman.product.infrastructure.persistence.jpa.entity.projection.ProductProjection;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
        componentModel = ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CategoryJpaEntityMapper.class}
)
public interface ProductJpaEntityMapper {
    Product toModel(ProductJpaEntity productEntity);

    Product toModel(ProductProjection productProjection);

    ProductJpaEntity toEntity(Product product);
}
