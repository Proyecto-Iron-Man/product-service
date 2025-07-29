package com.ironman.product.infrastructure.persistence.jpa.repository;

import com.ironman.product.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import com.ironman.product.infrastructure.persistence.jpa.entity.projection.ProductProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends CrudRepository<ProductJpaEntity, Long> {

    @Query("SELECT p.id AS id, p.name AS name, p.description AS description, p.price AS price, " +
            "p.stock AS stock, p.status AS status, p.createdAt AS createdAt, " +
            "p.category.id AS categoryId, p.category.name AS categoryName " +
            "FROM ProductJpaEntity p " +
            "WHERE (:#{#product.name} IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#product.name}, '%'))) " +
            "AND (:#{#product.categoryId} IS NULL OR p.categoryId = :#{#product.categoryId})" +
            "AND (:#{#product.stock} IS NULL OR p.stock <= :#{#product.stock})"
    )
    List<ProductProjection> findByQuery(@Param("product") ProductJpaEntity product);
}
