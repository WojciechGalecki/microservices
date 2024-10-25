package wg.microservices.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wg.api.core.recommendation.Recommendation;
import wg.microservices.services.RecommendationService;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Recommendation> getRecommendations(@RequestParam int productId) {
        return recommendationService.getRecommendations(productId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Recommendation createRecommendation(@Validated @RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    @DeleteMapping()
    public void deleteRecommendations(@RequestParam int productId) {
        recommendationService.deleteRecommendations(productId);
    }
}
