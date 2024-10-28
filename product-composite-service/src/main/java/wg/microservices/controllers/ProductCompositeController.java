package wg.microservices.controllers;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import wg.api.composite.product.ProductAggregate;
import wg.api.core.product.Product;
import wg.microservices.events.EventPublisher;
import wg.microservices.services.ProductCompositeService;

@RestController
@RequiredArgsConstructor
public class ProductCompositeController {
    private final ProductCompositeService productCompositeService;
    private final EventPublisher eventPublisher;

    @GetMapping(value = "/product-composite/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductAggregate> getProduct(@PathVariable int id) {
        return productCompositeService.getProduct(id);
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Product> createProduct(@Validated @RequestBody Product product) {
        return eventPublisher.createProduct(product);
    }

    @DeleteMapping("/products/{productId}")
    public Mono<Void> createProduct(@PathVariable int productId) {
        return eventPublisher.deleteProduct(productId);
    }
}
