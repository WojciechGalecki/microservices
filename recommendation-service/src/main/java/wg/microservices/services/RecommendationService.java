package wg.microservices.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import wg.api.core.recommendation.Recommendation;
import wg.exception.InvalidInputException;
import wg.microservices.mappers.RecommendationMapper;
import wg.microservices.persistence.RecommendationEntity;
import wg.microservices.persistence.RecommendationRepository;

@Slf4j
@Service
@AllArgsConstructor
public class RecommendationService {
    private final RecommendationMapper recommendationMapper;
    private final RecommendationRepository recommendationRepository;

    public Flux<Recommendation> getRecommendations(int productId) {
        log.info("Fetching recommendations for product id: {}", productId);
        return recommendationRepository.findByProductId(productId)
            .map(recommendationMapper::entityToApi);
    }

    public Mono<Recommendation> createRecommendation(Recommendation recommendation) {
        log.info("Creating new recommendation");
        RecommendationEntity recommendationEntity = recommendationMapper.apiToEntity(recommendation);
        return recommendationRepository.save(recommendationEntity)
            .onErrorMap(
                DuplicateKeyException.class,
                ex -> new InvalidInputException("Duplicate key, Product Id: " + recommendation.productId()))
            .map(recommendationMapper::entityToApi);
    }

    public Mono<Void> deleteRecommendations(int productId) {
        log.info("Deleting recommendations with productId: {}", productId);
        return recommendationRepository.deleteAll(recommendationRepository.findByProductId(productId));
    }
}
