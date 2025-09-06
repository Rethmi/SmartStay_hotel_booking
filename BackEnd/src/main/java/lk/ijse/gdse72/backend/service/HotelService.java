package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.entity.Hotel;

import java.util.List;

public interface HotelService {
    void saveHotel(HotelDto hotel);

    void deleteHotel(Long id);

    void updateHotel(Long id, HotelDto hotelDto);

    List<HotelDto> getAllHotels();

    Long getUserIdByEmail(String email);

    List<Hotel>getHotelsByUserId(Long userId);
}
