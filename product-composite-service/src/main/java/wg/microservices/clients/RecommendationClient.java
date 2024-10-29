package wg.microservices.clients;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import wg.api.core.recommendation.Recommendation;

@Slf4j
@Component
public class RecommendationClient {
    private static final String RECOMMENDATION_SVC_HOST = "recommendation";
    private static final String RECOMMENDATION_SVC_PATH = "/recommendations";

    private final WebClient webClient;

    public RecommendationClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

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
            .host(RECOMMENDATION_SVC_HOST)
            .path(RECOMMENDATION_SVC_PATH)
            .queryParam("productId", productId)
            .build()
            .toUri();
    }
}
