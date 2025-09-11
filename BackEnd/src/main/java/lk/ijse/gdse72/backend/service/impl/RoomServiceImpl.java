
package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.entity.Room;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.repository.RoomRepository;
import lk.ijse.gdse72.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    private RoomDTO mapToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomType(room.getRoomType())
                .price(room.getPrice())
                .available(room.getAvailable())
                .roomNumber(room.getRoomNumber())
                .image1(room.getImage1())
                .image2(room.getImage2())
                .image3(room.getImage3())
                .hotelID(room.getHotel().getId())
                .build();
    }

    private Room mapToEntity(RoomDTO dto) {
        Hotel hotel = hotelRepository.findById(dto.getHotelID())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + dto.getHotelID()));

        return Room.builder()
                .id(dto.getId())
                .roomType(dto.getRoomType())
                .price(dto.getPrice())
                .available(dto.getAvailable())
                .roomNumber(dto.getRoomNumber())
                .image1(dto.getImage1())
                .image2(dto.getImage2())
                .image3(dto.getImage3())
                .hotel(hotel)
                .build();
    }

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        Room room = mapToEntity(roomDTO);
        return mapToDTO(roomRepository.save(room));
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        Hotel hotel = hotelRepository.findById(roomDTO.getHotelID())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDTO.getHotelID()));

        existingRoom.setRoomType(roomDTO.getRoomType());
        existingRoom.setPrice(roomDTO.getPrice());
        existingRoom.setAvailable(roomDTO.getAvailable());
        existingRoom.setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.setImage1(roomDTO.getImage1());
        existingRoom.setImage2(roomDTO.getImage2());
        existingRoom.setImage3(roomDTO.getImage3());
        existingRoom.setHotel(hotel);

        return mapToDTO(roomRepository.save(existingRoom));
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomDTO> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotel_Id(hotelId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByAvailability(String status) {
        return roomRepository.findByAvailable(status).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}