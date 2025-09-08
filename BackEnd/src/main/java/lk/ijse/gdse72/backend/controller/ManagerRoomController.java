//// ManagerRoomController.java
//package lk.ijse.gdse72.backend.controller;
//
//import lk.ijse.gdse72.backend.dto.ResponseDTO;
//import lk.ijse.gdse72.backend.entity.Room;
//import lk.ijse.gdse72.backend.service.RoomService;
//import lk.ijse.gdse72.backend.service.impl.RoomServiceImpl;
//import lk.ijse.gdse72.backend.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin
//@RestController
//@RequestMapping("api/v1/ManagerRoom")
//public class ManagerRoomController {
//    private final RoomService roomService;
//    private final RoomServiceImpl roomServiceImpl;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    public ManagerRoomController(RoomService roomService, RoomServiceImpl roomServiceImpl) {
//        this.roomService = roomService;
//        this.roomServiceImpl = roomServiceImpl;
//    }
//
//    @GetMapping("/getAllRoomByHotelID")
//    @PreAuthorize("hasAnyAuthority('Manager')")
//    public ResponseEntity<ResponseDTO> getAllRoomByHotelID(@RequestParam Long hotelID,@RequestHeader("Authorization") String token) {
//        System.out.println("Hotel ID: " + hotelID);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
//        List<Room> rooms = roomService.getAllRoomsByHotelID(hotelID);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", rooms));
//    }
//}