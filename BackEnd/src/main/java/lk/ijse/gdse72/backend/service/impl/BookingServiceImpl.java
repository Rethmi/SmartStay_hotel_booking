package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.BookingDTO;
import lk.ijse.gdse72.backend.entity.Booking;
import lk.ijse.gdse72.backend.entity.Room;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.repository.BookingRepository;
import lk.ijse.gdse72.backend.repository.RoomRepository;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private BookingDTO convertToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .roomId(booking.getRoom().getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .phoneNumber(booking.getPhoneNumber())
                .email(booking.getEmail())
                .city(booking.getCity())
                .status(booking.getStatus())
                .build();
    }

    private Booking convertToEntity(BookingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + dto.getRoomId()));

        return Booking.builder()
                .id(dto.getId())
                .user(user)
                .room(room)
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .city(dto.getCity())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        String email = bookingDTO.getEmail();
        String subject = "Place a booking";
        return convertToDTO(bookingRepository.save(booking));
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + bookingDTO.getUserId()));
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingDTO.getRoomId()));

        existingBooking.setUser(user);
        existingBooking.setRoom(room);
        existingBooking.setCheckInDate(bookingDTO.getCheckInDate());
        existingBooking.setCheckOutDate(bookingDTO.getCheckOutDate());
        existingBooking.setPhoneNumber(bookingDTO.getPhoneNumber());
        existingBooking.setEmail(bookingDTO.getEmail());
        existingBooking.setCity(bookingDTO.getCity());
        existingBooking.setStatus(bookingDTO.getStatus());

        return convertToDTO(bookingRepository.save(existingBooking));
    }

    @Override
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingDTO> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByRoom(Long roomId) {
        return bookingRepository.findByRoomId(roomId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}