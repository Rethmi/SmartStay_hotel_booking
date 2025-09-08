package lk.ijse.gdse72.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String amenities;
    private String phoneNumber;
    private String image;
}