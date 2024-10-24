package wg.microservices.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import wg.api.core.review.Review;

@Service
public class ReviewService {

    public List<Review> getReviews(int productId) {
        return Collections.emptyList();
    }
}
