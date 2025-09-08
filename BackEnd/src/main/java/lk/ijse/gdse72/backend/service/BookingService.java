package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.BookingDTO;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getBookingById(Long id);
    List<BookingDTO> getAllBookings();
    BookingDTO updateBooking(Long id, BookingDTO bookingDTO);
    void deleteBooking(Long id);
    List<BookingDTO> getBookingsByUser(Long userId);
    List<BookingDTO> getBookingsByRoom(Long roomId);
}