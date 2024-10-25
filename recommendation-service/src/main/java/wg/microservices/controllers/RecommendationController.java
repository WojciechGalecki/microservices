package wg.microservices.controllers;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wg.api.core.recommendation.Recommendation;
import wg.microservices.services.RecommendationService;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Recommendation> getRecommendations(@RequestParam int productId) {
        return recommendationService.getRecommendations(productId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Recommendation> createRecommendation(@Validated @RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    @DeleteMapping()
    public Mono<Void> deleteRecommendations(@RequestParam int productId) {
        return recommendationService.deleteRecommendations(productId);
    }
}
