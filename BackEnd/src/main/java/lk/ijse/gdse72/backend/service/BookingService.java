package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.BookingDTO;
import lk.ijse.gdse72.backend.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingDTO save(BookingDTO bookingDTO);

    void delete(Long id);

    void update(Long id, BookingDTO bookingDTO);

    List<BookingDTO> getAll();

    List<Booking> getBookingsByHotel(Long hotelID);

}
