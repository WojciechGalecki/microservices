package wg.microservices.services;

import org.springframework.stereotype.Service;

import wg.api.core.product.Product;

@Service
public class ProductService {

    public Product getProduct(int id) {
        return new Product(1, "Test", 2);
    }
}
