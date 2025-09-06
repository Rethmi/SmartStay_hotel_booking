package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByRoomIdIn(List<Long> roomIds);

}
