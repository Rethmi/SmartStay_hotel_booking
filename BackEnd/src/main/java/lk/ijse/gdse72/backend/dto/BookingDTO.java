package lk.ijse.gdse72.backend.dto;

import lk.ijse.gdse72.backend.entity.Booking.BookingStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String phoneNumber;
    private String email;
    private String city;
    private BookingStatus status;
}
