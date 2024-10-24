package wg.microservices.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import wg.api.core.product.Product;

@Slf4j
@Service
public class ProductService {

    public Product getProduct(int id) {
        log.info("Fetching product with id: {}", id);
        return new Product(1, "Test", 2);
    }
}
