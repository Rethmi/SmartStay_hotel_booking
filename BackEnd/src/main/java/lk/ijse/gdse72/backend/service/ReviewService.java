package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    void save(ReviewDTO reviewDTO);

    void delete(Long id);

    void update(Long id, ReviewDTO reviewDTO);

    List<ReviewDTO> getAll();
}
