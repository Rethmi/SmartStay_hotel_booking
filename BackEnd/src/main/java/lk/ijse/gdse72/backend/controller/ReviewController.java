package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.ReviewDTO;
import lk.ijse.gdse72.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            ReviewDTO savedReview = reviewService.saveReview(reviewDTO);
            return ResponseEntity.ok(savedReview);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByHotelId(@PathVariable Long hotelId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByHotelId(hotelId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkReviewExists(
            @RequestParam Long userId,
            @RequestParam Long hotelId) {
        boolean exists = reviewService.existsByUserIdAndHotelId(userId, hotelId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/average-rating/{hotelId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long hotelId) {
        double averageRating = reviewService.getAverageRatingByHotelId(hotelId);
        return ResponseEntity.ok(averageRating);
    }
}