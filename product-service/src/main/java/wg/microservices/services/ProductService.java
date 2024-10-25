package wg.microservices.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import wg.api.core.product.Product;
import wg.exception.InvalidInputException;
import wg.exception.NotFoundException;
import wg.microservices.mappers.ProductMapper;
import wg.microservices.persistence.ProductEntity;
import wg.microservices.persistence.ProductRepository;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Mono<Product> getProduct(int productId) {
        log.info("Fetching product with productId: {}", productId);
        return productRepository.findByProductId(productId)
            .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + productId)))
            .map(productMapper::entityToApi);
    }

    public Mono<Product> createProduct(Product product) {
        log.info("Creating new product");
        ProductEntity productEntity = productMapper.apiToEntity(product);
        return productRepository.save(productEntity)
            .onErrorMap(
                DuplicateKeyException.class,
                ex -> new InvalidInputException("Duplicate key, Product Id: " + product.productId()))
            .map(productMapper::entityToApi);
    }

    public Mono<Void> deleteProduct(int productId) {
        log.info("Deleting product with productId: {}", productId);
        return productRepository.findByProductId(productId)
            .map(productRepository::delete)
            .log("Successfully deleted product with productId: " + productId)
            .flatMap(e -> e);
    }
}
