package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.entity.Room;

import java.util.List;

public interface RoomService {
    void save(RoomDTO roomDTO);

    void delete(Long id);

    void update(Long id, RoomDTO roomDTO);

    List<RoomDTO> getAll();

    List<Room> getAllRoomsByHotelID(Long id);
}
