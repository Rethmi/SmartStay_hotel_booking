//package lk.ijse.gdse72.backend.controller;
//
//import lk.ijse.gdse72.backend.dto.RoomDTO;
//import lk.ijse.gdse72.backend.service.RoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/rooms")
//@RequiredArgsConstructor
//public class RoomController {
//
//    private final RoomService roomService;
//
//    @PostMapping("/save")
//    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
//        return ResponseEntity.ok(roomService.createRoom(roomDTO));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
//        return ResponseEntity.ok(roomService.getRoomById(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RoomDTO>> getAllRooms() {
//        return ResponseEntity.ok(roomService.getAllRooms());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
//        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        roomService.deleteRoom(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/hotel/{hotelId}")
//    public ResponseEntity<List<RoomDTO>> getRoomsByHotel(@PathVariable Long hotelId) {
//        return ResponseEntity.ok(roomService.getRoomsByHotel(hotelId));
//    }
//}
package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomDTO>> getRoomsByHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getRoomsByHotel(hotelId));
    }

    @GetMapping("/available/{status}")
    public ResponseEntity<List<RoomDTO>> getRoomsByAvailability(@PathVariable String status) {
        return ResponseEntity.ok(roomService.getRoomsByAvailability(status));
    }
}