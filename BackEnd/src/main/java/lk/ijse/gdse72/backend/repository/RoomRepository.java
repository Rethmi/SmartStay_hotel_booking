package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotelId(Long hotelId);

    List<Room> findByHotelId(Long hotelID);
}
