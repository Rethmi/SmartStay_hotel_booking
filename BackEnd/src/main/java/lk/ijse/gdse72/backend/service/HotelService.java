package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.HotelDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {
    HotelDto saveHotel(HotelDto hotelDto, MultipartFile image);
    List<HotelDto> getAllHotels();
    HotelDto getHotelById(Long id);
    HotelDto updateHotel(HotelDto hotelDto);
    void deleteHotel(Long id);
//
//    List<HotelDto> searchHotels(String destination, String roomType, LocalDate checkin, LocalDate checkout);
//
}