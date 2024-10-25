package wg.microservices.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wg.api.core.review.Review;
import wg.exception.InvalidInputException;
import wg.microservices.mappers.ReviewMapper;
import wg.microservices.persistence.ReviewEntity;
import wg.microservices.persistence.ReviewRepository;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    public List<Review> getReviews(int productId) {
        log.info("Fetching reviews for product id: {}", productId);
        return reviewRepository.findByProductId(productId).stream()
            .map(reviewMapper::entityToApi)
            .toList();
    }

    public Review createReview(Review review) {
        log.info("Creating new review");
        try {
            ReviewEntity reviewEntity = reviewMapper.apiToEntity(review);
            ReviewEntity savedReviewEntity = reviewRepository.save(reviewEntity);
            return reviewMapper.entityToApi(savedReviewEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Duplicate key, Product Id: " + review.productId()
                + ", Review Id:" + review.reviewId());
        }
    }

    public void deleteReviews(int productId) {
        log.info("Deleting reviews with productId: {}", productId);
        reviewRepository.deleteAll(reviewRepository.findByProductId(productId));
    }
}
