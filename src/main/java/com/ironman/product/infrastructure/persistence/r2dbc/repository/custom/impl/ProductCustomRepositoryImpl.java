package com.ironman.product.infrastructure.persistence.r2dbc.repository.custom.impl;

import com.ironman.product.infrastructure.persistence.r2dbc.entity.CategoryR2dbcEntity;
import com.ironman.product.infrastructure.persistence.r2dbc.entity.ProductR2dbcEntity;
import com.ironman.product.infrastructure.persistence.r2dbc.repository.custom.ProductCustomRepository;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final DatabaseClient databaseClient;

    private static final String SELECT_PRODUCT_JOIN_CATEGORY = "SELECT " +
            "p.id, p.name, p.description, p.price, p.stock, " +
            "p.status, p.created_at, p.updated_at, " +
            "c.id AS category_id, c.name AS category_name, c.url_key, " +
            "c.description AS category_description, c.status AS category_status, " +
            "c.created_at AS category_created_at, c.updated_at AS category_updated_at " +
            "FROM products p JOIN categories c ON p.category_id = c.id ";


    @Override
    public Mono<ProductR2dbcEntity> findByIdJoinCategory(Long id) {
        String query = SELECT_PRODUCT_JOIN_CATEGORY + "WHERE p.id = :id";

        return databaseClient.sql(query)
                .bind("id", id)
                .map(mappingProduct())
                .one();
    }

    @Override
    public Flux<ProductR2dbcEntity> findByQuery(ProductR2dbcEntity product) {
        String WHERE = "WHERE ( :name IS NULL OR UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%')) ) " +
                "AND ( :stock IS NULL OR p.stock <= :stock ) " +
                "AND ( :categoryId IS NULL OR p.category_id = :categoryId ) ";

        String query = SELECT_PRODUCT_JOIN_CATEGORY + WHERE + "ORDER BY p.id DESC";

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query);

        if (product.getName() != null) {
            spec = spec.bind("name", product.getName());
        } else {
            spec = spec.bindNull("name", String.class);
        }

        if (product.getStock() != null) {
            spec = spec.bind("stock", product.getStock());
        } else {
            spec = spec.bindNull("stock", Integer.class);
        }

        if (product.getCategoryId() != null) {
            spec = spec.bind("categoryId", product.getCategoryId());
        } else {
            spec = spec.bindNull("categoryId", Long.class);
        }

        return spec.map(mappingProduct()).all();
    }

    private static BiFunction<Row, RowMetadata, ProductR2dbcEntity> mappingProduct() {
        return (row, metadata) -> {

            CategoryR2dbcEntity category = CategoryR2dbcEntity.builder()
                    .id(row.get("category_id", Long.class))
                    .name(row.get("category_name", String.class))
                    .description(row.get("category_description", String.class))
                    .urlKey(row.get("url_key", String.class))
                    .status(row.get("category_status", String.class))
                    .createdAt(row.get("category_created_at", LocalDateTime.class))
                    .updatedAt(row.get("category_updated_at", LocalDateTime.class))
                    .build();

            return ProductR2dbcEntity.builder()
                    .id(row.get("id", Long.class))
                    .name(row.get("name", String.class))
                    .description(row.get("description", String.class))
                    .price(row.get("price", BigDecimal.class))
                    .stock(row.get("stock", Integer.class))
                    .categoryId(row.get("category_id", Long.class))
                    .status(row.get("status", String.class))
                    .createdAt(row.get("created_at", LocalDateTime.class))
                    .updatedAt(row.get("updated_at", LocalDateTime.class))
                    .category(category)
                    .build();
        };
    }
}
