package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.ReviewDTO;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.entity.Review;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.repository.ReviewRepository;
import lk.ijse.gdse72.backend.repository.ReviewRepository;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        // Check if user and hotel exist
        Optional<User> user = userRepository.findById(reviewDTO.getUserId());
        Optional<Hotel> hotel = hotelRepository.findById(reviewDTO.getHotelId());

        if (user.isEmpty() || hotel.isEmpty()) {
            throw new RuntimeException("User or Hotel not found");
        }

        // Set current date if not provided
        if (reviewDTO.getReviewDate() == null) {
            reviewDTO.setReviewDate(LocalDate.now());
        }

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setUser(user.get());
        review.setHotel(hotel.get());

        Review savedReview = reviewRepository.save(review);
        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return modelMapper.map(reviews, new TypeToken<List<ReviewDTO>>(){}.getType());
    }

    @Override
    public List<ReviewDTO> getReviewsByHotelId(Long hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);
        return modelMapper.map(reviews, new TypeToken<List<ReviewDTO>>(){}.getType());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return modelMapper.map(reviews, new TypeToken<List<ReviewDTO>>(){}.getType());
    }

    @Override
    public boolean existsByUserIdAndHotelId(Long userId, Long hotelId) {
        return reviewRepository.existsByUserIdAndHotelId(userId, hotelId);
    }

    @Override
    public double getAverageRatingByHotelId(Long hotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(hotelId);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        double sum = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();

        return sum / reviews.size();
    }
}