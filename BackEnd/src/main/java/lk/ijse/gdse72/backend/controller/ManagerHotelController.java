// ManagerHotelController.java
package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.entity.Hotel;
import lk.ijse.gdse72.backend.service.HotelService;
import lk.ijse.gdse72.backend.service.impl.HotelServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/hotelManager")
public class ManagerHotelController {
    private final HotelService hotelService;
    private final HotelServiceImpl hotelServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    public ManagerHotelController(HotelService hotelService, HotelServiceImpl hotelServiceImpl) {
        this.hotelService = hotelService;
        this.hotelServiceImpl = hotelServiceImpl;
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('Manager')")
    public ResponseEntity<ResponseDTO> getHotelsByEmail(@RequestParam String email, @RequestHeader("Authorization") String token) {
        System.out.println("Email: " + email);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        Long userId = hotelService.getUserIdByEmail(email);
        System.out.println("User ID: " + userId);

        if (userId != null) {
            List<Hotel> hotels = hotelService.getHotelsByUserId(userId);

            if (!hotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(HttpStatus.OK.value(), "Success", hotels));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "No Hotels Found", null));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "User Not Found", null));
        }
    }
}