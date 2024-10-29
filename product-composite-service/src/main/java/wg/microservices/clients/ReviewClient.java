package wg.microservices.clients;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.api.core.review.Review;

@Slf4j
@Component
public class ReviewClient {
    private static final String REVIEW_SVC_HOST = "review";
    private static final String REVIEW_SVC_PATH = "/reviews";

    private final WebClient webClient;

    public ReviewClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Flux<Review> getReviews(int productId) {
        log.info("Fetching reviews for product id: {}", productId);
        return webClient.get()
            .uri(getUrl(productId))
            .retrieve()
            .bodyToFlux(Review.class);
    }

    private URI getUrl(int productId) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(REVIEW_SVC_HOST)
            .path(REVIEW_SVC_PATH)
            .queryParam("productId", productId)
            .build()
            .toUri();
    }
}
