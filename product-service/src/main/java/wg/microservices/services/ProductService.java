package wg.microservices.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Product getProduct(int productId) {
        log.info("Fetching product with productId: {}", productId);
        ProductEntity productEntity = productRepository.findByProductId(productId)
            .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));
        return productMapper.entityToApi(productEntity);
    }

    public Product createProduct(Product product) {
        log.info("Creating new product");
        try {
            ProductEntity productEntity = productMapper.apiToEntity(product);
            ProductEntity savedProductEntity = productRepository.save(productEntity);
            return productMapper.entityToApi(savedProductEntity);
        } catch (DuplicateKeyException ex) {
            throw new InvalidInputException("Duplicate key, Product Id: " + product.productId());
        }
    }

    public void deleteProduct(int productId) {
        log.info("Deleting product with productId: {}", productId);
        productRepository.findByProductId(productId).ifPresentOrElse(p -> {
            productRepository.delete(p);
            log.info("Successfully deleted product with productId: {}", productId);
        }, () -> log.info("Couldn't find product with productId: {}", productId));
    }
}
