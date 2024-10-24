package wg.microservices.clients;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final RestTemplate restTemplate;

    public List<Review> getReviews(int productId) {
        log.info("Fetching reviews for product id: {}", productId);
        return restTemplate.exchange(getUrl(productId), GET, EMPTY, new ParameterizedTypeReference<List<Review>>() {
        }).getBody();
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
