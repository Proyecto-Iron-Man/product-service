package com.ironman.product.infrastructure.presentation.controller;

import com.ironman.product.application.dto.*;
import com.ironman.product.application.exception.ExceptionResponse;
import com.ironman.product.application.service.ProductService;
import com.ironman.product.infrastructure.presentation.util.HttpStatusCode;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.OK,
                    description = "Products found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductSummaryResponse.class
                                    )
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Flux<ProductSummaryResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll());
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.OK,
                    description = "Product found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProductDetailResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.NOT_FOUND,
                    description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mono<ProductDetailResponse>> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findById(id));
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.CREATED,
                    description = "Product created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.BAD_REQUEST,
                    description = "Invalid request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Mono<ProductResponse>> create(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.create(productRequest));
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.CREATED,
                    description = "Product updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.BAD_REQUEST,
                    description = "Invalid request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.NOT_FOUND,
                    description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Mono<ProductResponse>> update(
            @PathVariable("id") Long id, @Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.update(id, productRequest));
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.CREATED,
                    description = "Product disabled",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ProductResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.BAD_REQUEST,
                    description = "Invalid request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.NOT_FOUND,
                    description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<ProductResponse>> disable(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.disable(id));
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = HttpStatusCode.OK,
                    description = "Products found by query",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ProductOverviewResponse.class
                                    )
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.NOT_FOUND,
                    description = "Products not found by query",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = HttpStatusCode.INTERNAL_SERVER_ERROR,
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ExceptionResponse.class
                            )
                    )
            )
    })
    @GetMapping("/query")
    public ResponseEntity<Flux<ProductOverviewResponse>> findByQuery(ProductQueryRequest productQueryRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findByQuery(productQueryRequest));
    }
}
