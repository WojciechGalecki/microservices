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
import wg.api.core.recommendation.Recommendation;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationClient {
    private static final String RECOMMENDATION_SERVICE_PATH = "/recommendations";

    @Value("${app.recommendation-service.host}")
    String recommendationServiceHost;
    @Value("${app.recommendation-service.port}")
    int recommendationServicePort;

    private final RestTemplate restTemplate;

    public List<Recommendation> getRecommendations(int productId) {
        log.info("Fetching recommendations for product id: {}", productId);
        return restTemplate.exchange(getUrl(productId), GET, EMPTY, new ParameterizedTypeReference<List<Recommendation>>() {
        }).getBody();
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
