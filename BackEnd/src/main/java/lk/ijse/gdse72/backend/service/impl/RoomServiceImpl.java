package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.entity.Room;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.repository.RoomRepository;
import lk.ijse.gdse72.backend.service.RoomService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void save(RoomDTO roomDTO) {
       roomRepository.save(modelMapper.map(roomDTO,Room.class));
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public void update(Long id, RoomDTO roomDTO) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));

        // Update fields
        existingRoom.setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.setRoomType(roomDTO.getRoomType());
        existingRoom.setPrice(roomDTO.getPrice());
        existingRoom.setAvailable(roomDTO.getAvailable());
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelID())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomDTO.getHotelID()));
        existingRoom.setHotel(hotel);
        existingRoom.setImage1(roomDTO.getImage1());
        existingRoom.setImage2(roomDTO.getImage2());
        existingRoom.setImage3(roomDTO.getImage3());

        // Save the updated entity
        roomRepository.save(existingRoom);
    }

    @Override
    public List<RoomDTO> getAll() {
        modelMapper.addConverter(new Converter<Room, List<String>>() {
            public List<String> convert(MappingContext<Room, List<String>> context) {
                Room room = context.getSource();
                List<String> images = new ArrayList<>();
                if (room.getImage1() != null) images.add(room.getImage1());
                if (room.getImage2() != null) images.add(room.getImage2());
                if (room.getImage3() != null) images.add(room.getImage3());
                return images;
            }
        });

        return modelMapper.map(roomRepository.findAll(), new TypeToken<List<RoomDTO>>() {}.getType());
    }

    @Override
    public List<Room> getAllRoomsByHotelID(Long id) {
        return roomRepository.findAllByHotelId(id);
    }


}
