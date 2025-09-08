package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel_Id(Long hotelId);
    List<Room> findByAvailable(String available);
}
