package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.BookingDTO;
import lk.ijse.gdse72.backend.entity.Booking;
import lk.ijse.gdse72.backend.entity.Room;
import lk.ijse.gdse72.backend.repository.BookingRepository;
import lk.ijse.gdse72.backend.repository.RoomRepository;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.BookingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        bookingRepository.save(modelMapper.map(bookingDTO, Booking.class));
        return bookingDTO;

    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public void update(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found."));
        existingBooking.setStatus(Booking.BookingStatus.valueOf(bookingDTO.getStatus()));

        bookingRepository.save(existingBooking);
    }

    @Override
    public List<BookingDTO> getAll() {

        return modelMapper.map(bookingRepository.findAll(), new TypeToken<List<BookingDTO>>() {}.getType());
    }

    @Override
    public List<Booking> getBookingsByHotel(Long hotelID) {
        List<Room> rooms = roomRepository.findByHotelId(hotelID);

        List<Long> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());

        return bookingRepository.findByRoomIdIn(roomIds);
    }

}
