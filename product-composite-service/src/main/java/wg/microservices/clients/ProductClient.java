package wg.microservices.clients;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import wg.api.core.product.Product;

@Slf4j
@Component
public class ProductClient {
    private static final String PRODUCT_SVC_HOST = "product";
    private static final String PRODUCT_SVC_PATH = "/products/{id}";

    private final WebClient webClient;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Product> getProduct(int id) {
        log.info("Fetching product with id: {}", id);
        return webClient.get()
            .uri(getUrl(id))
            .retrieve()
            .bodyToMono(Product.class);
    }

    private URI getUrl(int id) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(PRODUCT_SVC_HOST)
            .path(PRODUCT_SVC_PATH)
            .buildAndExpand(id)
            .toUri();
    }
}
