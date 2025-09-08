package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.HotelDto;

import java.util.List;

public interface HotelService {
    HotelDto saveHotel(HotelDto hotelDto);
    List<HotelDto> getAllHotels();
    HotelDto getHotelById(Long id);
    HotelDto updateHotel(HotelDto hotelDto);
    void deleteHotel(Long id);
}