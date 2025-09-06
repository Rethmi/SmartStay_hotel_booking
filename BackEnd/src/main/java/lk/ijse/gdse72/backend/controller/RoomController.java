// RoomController.java
package lk.ijse.gdse72.backend.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.service.RoomService;
import lk.ijse.gdse72.backend.service.impl.RoomServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/room")
public class RoomController {
    private final RoomService roomService;
    private final RoomServiceImpl roomServiceImpl;

    @Autowired
    JwtUtil jwtUtil;

    public RoomController(RoomService roomService, RoomServiceImpl roomServiceImpl) {
        this.roomService = roomService;
        this.roomServiceImpl = roomServiceImpl;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> saveRoom(@RequestBody @Valid RoomDTO roomDTO,@RequestHeader("Authorization") String token) {
        System.out.println("hotelID"+" "+roomDTO.getHotelID());
        System.out.println(roomDTO.getImage1());
        System.out.println(roomDTO.getImage2());
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        roomServiceImpl.save(roomDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Room Saved Successfully", null));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity <ResponseDTO> deleteRoom(@PathVariable Long id,@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        roomService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", null));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','Manager')")
    public ResponseEntity<ResponseDTO> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomDTO roomDTO,@RequestHeader("Authorization") String token) {
        System.out.println(roomDTO.getImage1()+"   Image 1");
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        roomServiceImpl.update(id,roomDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Room Updated Successfully", null));
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getAllRooms(@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", roomService.getAll()));
    }

    @GetMapping("/getByHotelId/{hotelId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getAllRoomsByHotelID(@PathVariable Long hotelId ,@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", roomService.getAllRoomsByHotelID(hotelId)));
    }
}