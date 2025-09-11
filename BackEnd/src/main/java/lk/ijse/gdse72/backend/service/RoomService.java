
package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import java.util.List;

public interface RoomService {
    RoomDTO createRoom(RoomDTO roomDTO);
    RoomDTO getRoomById(Long id);
    List<RoomDTO> getAllRooms();
    RoomDTO updateRoom(Long id, RoomDTO roomDTO);
    void deleteRoom(Long id);
    List<RoomDTO> getRoomsByHotel(Long hotelId);
    List<RoomDTO> getRoomsByAvailability(String status);
}