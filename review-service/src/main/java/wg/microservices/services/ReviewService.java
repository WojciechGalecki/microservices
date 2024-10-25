package wg.microservices.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
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
    private final Scheduler jdbcScheduler;

    public Flux<Review> getReviews(int productId) {
        log.info("Fetching reviews for product id: {}", productId);
        return Mono.fromCallable(() -> internalGetReviews(productId))
            .flatMapMany(Flux::fromIterable)
            .subscribeOn(jdbcScheduler);
    }

    public Mono<Review> createReview(Review review) {
        log.info("Creating new review");
        return Mono.fromCallable(() -> internalCreateReview(review))
            .subscribeOn(jdbcScheduler);
    }

    public Mono<Void> deleteReviews(int productId) {
        log.info("Deleting reviews with productId: {}", productId);
        return Mono.fromRunnable(() -> internalDeleteReviews(productId))
            .subscribeOn(jdbcScheduler)
            .then();
    }

    private Review internalCreateReview(Review review) {
        try {
            ReviewEntity reviewEntity = reviewMapper.apiToEntity(review);
            ReviewEntity savedReviewEntity = reviewRepository.save(reviewEntity);
            return reviewMapper.entityToApi(savedReviewEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new InvalidInputException("Duplicate key, Product Id: " + review.productId()
                + ", Review Id:" + review.reviewId());        }
    }

    private List<Review> internalGetReviews(int productId) {
        return reviewRepository.findByProductId(productId).stream()
            .map(reviewMapper::entityToApi)
            .toList();
    }

    private void internalDeleteReviews(int productId) {
        reviewRepository.deleteAll(reviewRepository.findByProductId(productId));
    }
}
