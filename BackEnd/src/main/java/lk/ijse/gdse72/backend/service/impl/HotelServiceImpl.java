package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.repository.HotelRepository;
import lk.ijse.gdse72.backend.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final CloudService cloudService;

    @Override
    public HotelDto saveHotel(HotelDto hotelDto, MultipartFile image) {
        String url = uploadMultipleFiles(image);
        Hotel hotel = Hotel.builder()
                .name(hotelDto.getName())
                .location(hotelDto.getLocation())
                .description(hotelDto.getDescription())
                .amenities(hotelDto.getAmenities())
                .phoneNumber(hotelDto.getPhoneNumber())
                .image(url)
                .build();
        hotel = hotelRepository.save(hotel);
        return convertToDto(hotel);
    }
    public String uploadMultipleFiles(MultipartFile file) {
        String imageUrls = "";
            try {
                imageUrls = cloudService.uploadFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
            }
        return imageUrls;
    }

    @Override
    public List<HotelDto> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        return convertToDto(hotel);
    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelDto.getId()));

        existingHotel.setName(hotelDto.getName());
        existingHotel.setLocation(hotelDto.getLocation());
        existingHotel.setDescription(hotelDto.getDescription());
        existingHotel.setAmenities(hotelDto.getAmenities());
        existingHotel.setPhoneNumber(hotelDto.getPhoneNumber());
        existingHotel.setImage(hotelDto.getImage());

        Hotel updatedHotel = hotelRepository.save(existingHotel);
        return convertToDto(updatedHotel);
    }

    @Override
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

//    @Override
//    public List<HotelDto> searchHotels(String destination, String roomType, LocalDate checkin, LocalDate checkout) {
//        return List.of();
//    }

    private HotelDto convertToDto(Hotel hotel) {
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .location(hotel.getLocation())
                .description(hotel.getDescription())
                .amenities(hotel.getAmenities())
                .phoneNumber(hotel.getPhoneNumber())
                .image(hotel.getImage())
                .build();
    }
}