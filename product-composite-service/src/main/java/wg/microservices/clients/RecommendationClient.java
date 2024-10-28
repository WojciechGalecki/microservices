package wg.microservices.clients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.api.core.recommendation.Recommendation;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationClient {
    private static final String RECOMMENDATION_SERVICE_PATH = "/recommendations";

    @Value("${app.recommendation-service.host}")
    private String recommendationServiceHost;
    @Value("${app.recommendation-service.port}")
    private int recommendationServicePort;

    private final WebClient webClient;

    public Flux<Recommendation> getRecommendations(int productId) {
        log.info("Fetching recommendations for product id: {}", productId);
        return webClient.get()
            .uri(getUrl(productId))
            .retrieve()
            .bodyToFlux(Recommendation.class);
    }

    private URI getUrl(int productId) {
        return UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(recommendationServiceHost)
            .port(recommendationServicePort)
            .path(RECOMMENDATION_SERVICE_PATH)
            .queryParam("productId", productId)
            .build()
            .toUri();
    }
}
