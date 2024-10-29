package wg.gateway.health;

import java.net.URI;
import java.util.logging.Level;

import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HealthcheckClient {
    private static final String PRODUCT_SVC_HOST = "product";
    private static final String RECOMMENDATION_SVC_HOST = "recommendation";
    private static final String REVIEW_SVC_HOST = "review";
    private static final String HEALTH_PATH = "/actuator/health";

    private final WebClient webClient;

    public HealthcheckClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Health> getProductHealth() {
        return getHealth(getUrl(PRODUCT_SVC_HOST));
    }
    public Mono<Health> getRecommendationHealth() {
        return getHealth(getUrl(RECOMMENDATION_SVC_HOST));
    }
    public Mono<Health> getReviewHealth() {
        return getHealth(getUrl(REVIEW_SVC_HOST));
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

    private URI getUrl(String host) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(host)
            .path(HEALTH_PATH)
            .build()
            .toUri();
    }
}
