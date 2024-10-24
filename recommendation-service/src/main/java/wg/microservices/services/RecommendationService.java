package wg.microservices.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import wg.api.core.recommendation.Recommendation;

@Service
public class RecommendationService {

    public List<Recommendation> getRecommendations(int productId) {
        return Collections.emptyList();
    }
}
