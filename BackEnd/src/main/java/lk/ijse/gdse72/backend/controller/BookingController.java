// BookingController.java
package lk.ijse.gdse72.backend.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse72.backend.dto.BookingDTO;
import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.service.BookingService;
import lk.ijse.gdse72.backend.service.EmailService;
import lk.ijse.gdse72.backend.service.impl.BookingServiceImpl;
import lk.ijse.gdse72.backend.service.impl.EmailServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/booking")
public class BookingController {
    private final BookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final EmailService emailService;
    @Value("${app.domain}")
    private String appDomain;

    @Autowired
    JwtUtil jwtUtil;

    public BookingController(BookingService bookingService, BookingServiceImpl bookingServiceImpl, EmailServiceImpl emailServiceImpl, EmailService emailService) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
        this.emailService = emailService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveBooking(@RequestBody @Valid BookingDTO bookingDTO, @RequestHeader("Authorization") String token) {
        System.out.println("Check-in Date: " + bookingDTO.getCheckInDate());
        System.out.println("Check-out Date: " + bookingDTO.getCheckOutDate());
        System.out.println("User Email: " + bookingDTO.getEmail());

//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        bookingServiceImpl.save(bookingDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Booking Saved Successfully", null));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> deleteBooking(@PathVariable Long id, @RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        bookingService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", null));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'Manager')")
    public ResponseEntity<ResponseDTO> updateBooking(@PathVariable Long id,
                                                     @RequestBody @Valid BookingDTO bookingDTO,
                                                     @RequestHeader("Authorization") String token) {
        System.out.println(bookingDTO.getEmail());

//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));

        bookingServiceImpl.update(id, bookingDTO);

        if ("CONFIRMED".equals(bookingDTO.getStatus())) {
            String userEmail = bookingDTO.getEmail();

            String bookingDetails = "Check-in Date: " + bookingDTO.getCheckInDate() + "<br>" +
                    "Check-out Date: " + bookingDTO.getCheckOutDate() + "<br>" +
                    "Room ID: " + bookingDTO.getRoomId() + "<br>" +
                    "Booking ID: " + id;
            System.out.println("Booking ID_"+id);

            String paymentLink = "https://smart-hotel-booking-28c26.web.app/index?bookingId=" + id;

            emailService.sendBookingConfirmationEmail(userEmail, bookingDetails, paymentLink);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Booking Updated Successfully", null));
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllBookings(@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", bookingService.getAll()));
    }
}