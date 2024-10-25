package wg.microservices.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import wg.api.composite.product.ProductAggregate;
import wg.microservices.services.ProductCompositeService;

@RestController
@RequiredArgsConstructor
public class ProductCompositeController {
    private final ProductCompositeService productCompositeService;

    @GetMapping(value = "/product-composite/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ProductAggregate> getProduct(@PathVariable int id) {
        return productCompositeService.getProduct(id);
    }
}
