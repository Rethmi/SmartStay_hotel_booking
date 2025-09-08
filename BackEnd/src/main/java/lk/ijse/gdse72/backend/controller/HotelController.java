package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/save")
    public ResponseEntity<HotelDto> saveHotel(@RequestBody HotelDto hotelDto){
        return ResponseEntity.ok(hotelService.saveHotel(hotelDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<HotelDto>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

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
