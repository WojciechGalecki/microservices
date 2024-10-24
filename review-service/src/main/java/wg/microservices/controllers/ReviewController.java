package wg.microservices.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wg.api.core.review.Review;
import wg.microservices.services.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getReviews(@RequestParam int productId) {
        return reviewService.getReviews(productId);
    }
}
