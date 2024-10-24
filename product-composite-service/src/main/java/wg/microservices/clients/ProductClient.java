package wg.microservices.clients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wg.api.core.product.Product;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClient {
    private static final String PRODUCT_SERVICE_PATH = "/products/{id}";

    @Value("${app.product-service.host}")
    private String productServiceHost;
    @Value("${app.product-service.port}")
    private int productServicePort;

    private final RestTemplate restTemplate;

    public Product getProduct(int id) {
        log.info("Fetching product with id: {}", id);
        return restTemplate.getForObject(getUrl(id), Product.class);
    }

    private URI getUrl(int id) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(productServiceHost)
            .port(productServicePort)
            .path(PRODUCT_SERVICE_PATH)
            .buildAndExpand(id)
            .toUri();
    }
}
