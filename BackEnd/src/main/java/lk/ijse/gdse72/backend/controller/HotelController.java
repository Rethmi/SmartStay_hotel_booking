package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/save")
    public ResponseEntity<HotelDto> saveHotel(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("description") String description,
            @RequestParam("amenities") String amenities,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setName(name);
        hotelDto.setLocation(location);
        hotelDto.setDescription(description);
        hotelDto.setAmenities(amenities);
        hotelDto.setPhoneNumber(phoneNumber);

//        if (image != null && !image.isEmpty()) {
//            // Save the image file somewhere (filesystem, cloud, etc.)
//            String imageName = image.getOriginalFilename();
//            // Example: save to /uploads folder
//            Path uploadPath = Paths.get("uploads/" + imageName);
//            try {
//                Files.createDirectories(uploadPath.getParent());
//                image.transferTo(uploadPath);
//                hotelDto.setImage(imageName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return ResponseEntity.ok(hotelService.saveHotel(hotelDto,image));
    }


    @GetMapping("/all")
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<HotelDto>> searchHotels(
//            @RequestParam(required = false) String destination,
//            @RequestParam(required = false) String roomType,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout) {
//
//        return ResponseEntity.ok(hotelService.searchHotels(destination, roomType, checkin, checkout));
//    }
    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotel(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto) {
        hotelDto.setId(id);
        return ResponseEntity.ok(hotelService.updateHotel(hotelDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("Hotel deleted successfully");
    }
}
