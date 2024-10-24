package wg.microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wg.api.composite.product.ProductAggregate;
import wg.api.composite.product.RecommendationSummary;
import wg.api.composite.product.ReviewSummary;
import wg.api.core.product.Product;
import wg.api.core.recommendation.Recommendation;
import wg.api.core.review.Review;
import wg.microservices.clients.ProductClient;
import wg.microservices.clients.RecommendationClient;
import wg.microservices.clients.ReviewClient;

@Service
@RequiredArgsConstructor
public class ProductCompositeService {
    private final ProductClient productClient;
    private final RecommendationClient recommendationClient;
    private final ReviewClient reviewClient;

    public ProductAggregate getProduct(int productId) {
        Product product = productClient.getProduct(productId);
        List<Recommendation> recommendations = recommendationClient.getRecommendations(productId);
        List<Review> reviews = reviewClient.getReviews(productId);

        return new ProductAggregate(
            productId,
            product.name(),
            product.weight(),
            getRecommendationSummaries(recommendations),
            getReviewSummaries(reviews)
        );
    }

    private List<RecommendationSummary> getRecommendationSummaries(List<Recommendation> recommendations) {
        return recommendations.stream()
            .map(r -> new RecommendationSummary(r.recommendationId(), r.author(), r.rate()))
            .toList();
    }

    private List<ReviewSummary> getReviewSummaries(List<Review> reviews) {
        return reviews.stream()
            .map(r -> new ReviewSummary(r.reviewId(), r.author(), r.subject()))
            .toList();
    }
}
