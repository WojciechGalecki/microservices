package wg.microservices.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wg.api.core.recommendation.Recommendation;
import wg.microservices.services.RecommendationService;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping(value = "/recommendations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Recommendation> getRecommendations(@RequestParam int productId) {
        return recommendationService.getRecommendations(productId);
    }
}
