package wg.microservices.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import wg.api.core.recommendation.Recommendation;

@Slf4j
@Service
public class RecommendationService {

    public List<Recommendation> getRecommendations(int productId) {
        log.info("Fetching recommendations for product id: {}", productId);
        return Collections.emptyList();
    }
}
