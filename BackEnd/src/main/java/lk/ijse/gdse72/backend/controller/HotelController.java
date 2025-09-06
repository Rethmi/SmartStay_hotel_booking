// HotelController.java
package lk.ijse.gdse72.backend.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse72.backend.dto.HotelDto;
import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.service.HotelService;
import lk.ijse.gdse72.backend.service.impl.HotelServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    public HotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> saveHotel(@RequestBody @Valid HotelDto hotelDto,@RequestHeader("Authorization") String token){
        System.out.println(hotelDto.getUserId());
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelService.saveHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Hotel Saved Successfully", null));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity <ResponseDTO> deleteHotel(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        System.out.println("Delete Hotel ID: " + id);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelService.deleteHotel(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", null));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> updateHotel(@PathVariable Long id, @RequestBody @Valid HotelDto hotelDto,@RequestHeader("Authorization") String token) {
        System.out.println(hotelDto.getImage());
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        hotelServiceImpl.updateHotel(id,hotelDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Hotel Updated Successfully", null));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager','USER')")
    public ResponseEntity<ResponseDTO>getAllHotels(@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(),"Success",hotelServiceImpl.getAllHotels()));
    }
}