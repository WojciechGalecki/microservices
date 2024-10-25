package wg.microservices.services;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<Recommendation> getRecommendations(int productId) {
        log.info("Fetching recommendations for product id: {}", productId);
        return recommendationRepository.findByProductId(productId).stream()
            .map(recommendationMapper::entityToApi)
            .toList();
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        log.info("Creating new recommendation");
        try {
            RecommendationEntity recommendationEntity = recommendationMapper.apiToEntity(recommendation);
            RecommendationEntity savedRecommendationEntity = recommendationRepository.save(recommendationEntity);
            return recommendationMapper.entityToApi(savedRecommendationEntity);
        } catch (DuplicateKeyException ex) {
            throw new InvalidInputException("Duplicate key, Product Id: " + recommendation.productId()
                + ", Recommendation Id:" + recommendation.recommendationId());
        }
    }

    public void deleteRecommendations(int productId) {
        log.info("Deleting recommendations with productId: {}", productId);
        recommendationRepository.deleteAll(recommendationRepository.findByProductId(productId));
    }
}
