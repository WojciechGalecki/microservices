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
import wg.api.core.review.Review;
import wg.microservices.services.ReviewService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Review> getReviews(@RequestParam int productId) {
        return reviewService.getReviews(productId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Review createReview(@Validated @RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @DeleteMapping()
    public void deleteReviews(@RequestParam int productId) {
        reviewService.deleteReviews(productId);
    }
}
