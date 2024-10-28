package wg.microservices.clients;

import java.net.URI;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthClient {
    private static final String HEALTH_PATH = "/actuator/health";

    @Value("${app.product-service.host}") String productServiceHost;
    @Value("${app.product-service.port}") int productServicePort;
    @Value("${app.recommendation-service.host}") String recommendationServiceHost;
    @Value("${app.recommendation-service.port}") int recommendationServicePort;
    @Value("${app.review-service.host}") String reviewServiceHost;
    @Value("${app.review-service.port}") int reviewServicePort;

    private final WebClient webClient;

    public Mono<Health> getProductHealth() {
        return getHealth(getUrl(productServiceHost, productServicePort));
    }
    public Mono<Health> getRecommendationHealth() {
        return getHealth(getUrl(recommendationServiceHost, recommendationServicePort));
    }
    public Mono<Health> getReviewHealth() {
        return getHealth(getUrl(reviewServiceHost, reviewServicePort));
    }

    private Mono<Health> getHealth(URI url) {
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class)
            .map(s -> new Health.Builder().up().build())
            .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()))
            .log(log.getName(), Level.FINE);
    }

    private URI getUrl(String host, int port) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(host)
            .port(port)
            .path(HEALTH_PATH)
            .build()
            .toUri();
    }
}
