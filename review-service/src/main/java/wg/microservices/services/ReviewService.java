package wg.microservices.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import wg.api.core.review.Review;

@Slf4j
@Service
public class ReviewService {

    public List<Review> getReviews(int productId) {
        log.info("Fetching reviews for product id: {}", productId);
        return Collections.emptyList();
    }
}
