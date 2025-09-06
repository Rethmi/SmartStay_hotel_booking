// ManagerBookingController.java
package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.entity.Booking;
import lk.ijse.gdse72.backend.service.BookingService;
import lk.ijse.gdse72.backend.service.impl.BookingServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/ManagerBooking")
public class ManagerBookingController {
    private final BookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    public ManagerBookingController(BookingService bookingService, BookingServiceImpl bookingServiceImpl) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;
    }

    @GetMapping("getAllHotelBookings")
    @PreAuthorize("hasAnyAuthority('Manager')")
    public ResponseEntity<ResponseDTO> getBookingsByHotel(@RequestParam Long hotelID,@RequestHeader("Authorization") String token) {
        System.out.println("Fetching bookings for hotel ID: " + hotelID);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        List<Booking> bookings = bookingService.getBookingsByHotel(hotelID);
        System.out.println("Bookings found: " + bookings.size());

        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("Room ID: " + booking.getRoom().getId());
            System.out.println("Room Number: " + booking.getRoom().getRoomNumber());
            System.out.println("User ID: " + booking.getUser().getId());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", bookings));
    }
}