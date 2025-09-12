package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getAllReviews();
    List<ReviewDTO> getReviewsByHotelId(Long hotelId);
    List<ReviewDTO> getReviewsByUserId(Long userId);
    boolean existsByUserIdAndHotelId(Long userId, Long hotelId);
    double getAverageRatingByHotelId(Long hotelId);
}