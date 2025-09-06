package lk.ijse.gdse72.backend.dto;

import java.time.LocalDate;

public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long hotelId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    public ReviewDTO(Long id, Long userId, Long hotelId, int rating, String comment, LocalDate reviewDate) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public ReviewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
