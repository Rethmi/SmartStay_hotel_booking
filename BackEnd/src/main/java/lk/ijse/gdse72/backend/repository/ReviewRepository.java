package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHotelId(Long hotelId);

    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.hotel.id = :hotelId ORDER BY r.reviewDate DESC")
    List<Review> findReviewsByHotelIdOrderByDateDesc(@Param("hotelId") Long hotelId);

    boolean existsByUserIdAndHotelId(Long userId, Long hotelId);
}