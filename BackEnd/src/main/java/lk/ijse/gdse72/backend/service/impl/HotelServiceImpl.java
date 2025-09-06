package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.HotelService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override

    public void saveHotel(HotelDto hotel) {
        hotelRepository.save(modelMapper.map(hotel,Hotel.class));
    }

    @Override
    public void deleteHotel(Long id) {
        System.out.println("Delete Hotel Service ID: " + id);
        hotelRepository.deleteById(id);
    }

    @Override
    public void updateHotel(Long id, HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + id));

        // Update fields
        existingHotel.setName(hotelDto.getName());
        existingHotel.setLocation(hotelDto.getLocation());
        existingHotel.setDescription(hotelDto.getDescription());
        existingHotel.setAmenities(hotelDto.getAmenities());
        existingHotel.setPhoneNumber(hotelDto.getPhoneNumber());
        existingHotel.setImage(hotelDto.getImage());

        // Save the updated entity
        hotelRepository.save(existingHotel);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        return modelMapper.map(hotelRepository.findAll(), new TypeToken<List<HotelDto>>() {}.getType());

    }
    @Override
    public List<Hotel> getHotelsByUserId(Long userId) {
        System.out.println("Service");
        return hotelRepository.findByUserId(userId);
    }
    @Override
    public Long getUserIdByEmail(String email) {
        Long user = userRepository.findIdByEmailADD(email);
        return user;
    }

}
