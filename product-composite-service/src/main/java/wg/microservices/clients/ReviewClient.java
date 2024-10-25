package wg.microservices.clients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.api.core.review.Review;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewClient {
    private static final String REVIEW_SERVICE_PATH = "/reviews";

    @Value("${app.review-service.host}")
    private String reviewServiceHost;
    @Value("${app.review-service.port}")
    private int reviewServicePort;

    private final WebClient webClient;

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
            .host(reviewServiceHost)
            .port(reviewServicePort)
            .path(REVIEW_SERVICE_PATH)
            .queryParam("productId", productId)
            .build()
            .toUri();
    }
}
