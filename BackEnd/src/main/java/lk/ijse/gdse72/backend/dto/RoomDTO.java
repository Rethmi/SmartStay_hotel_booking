package lk.ijse.gdse72.backend.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long id;
    private String roomType;
    private double price;
    private String available;
    private Long roomNumber;
    private String image1;
    private String image2;
    private String image3;
    private Long hotelID;
    private List<Long> bookingIds;
}
