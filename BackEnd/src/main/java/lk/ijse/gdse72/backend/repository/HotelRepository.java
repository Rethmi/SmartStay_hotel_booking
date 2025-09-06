package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository  extends JpaRepository<Hotel,Long> {
    Long id(Long id);

    @Query("SELECT h FROM Hotel h WHERE h.user.id = :userId")
    List<Hotel> findByUserId(@Param("userId") Long userId);
//    List<Hotel> findByUserId(Long userId);
}
