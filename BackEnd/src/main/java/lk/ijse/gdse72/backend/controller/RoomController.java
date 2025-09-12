//
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
//@CrossOrigin(origins = "*")
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
//    @GetMapping("/all")
//    public ResponseEntity<List<RoomDTO>> getAllRooms() {
//        return ResponseEntity.ok(roomService.getAllRooms());
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
//        return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        roomService.deleteRoom(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/hotel/{hotelId}")
//    public ResponseEntity<List<RoomDTO>> getRoomsByHotel(@PathVariable Long hotelId) {
//        return ResponseEntity.ok(roomService.getRoomsByHotel(hotelId));
//    }
//
//    @GetMapping("/available/{status}")
//    public ResponseEntity<List<RoomDTO>> getRoomsByAvailability(@PathVariable String status) {
//        return ResponseEntity.ok(roomService.getRoomsByAvailability(status));
//    }
//}
package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.RoomDTO;
import lk.ijse.gdse72.backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    @Value("${file.upload-dir:uploads/rooms}")
    private String uploadDir;

    @PostMapping("/save")
    public ResponseEntity<RoomDTO> createRoom(
            @RequestParam("roomType") String roomType,
            @RequestParam("price") double price,
            @RequestParam("available") String available,
            @RequestParam("roomNumber") Long roomNumber,
            @RequestParam("hotelID") Long hotelID,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2,
            @RequestParam(value = "image3", required = false) MultipartFile image3) {

        try {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomType(roomType);
            roomDTO.setPrice(price);
            roomDTO.setAvailable(available);
            roomDTO.setRoomNumber(roomNumber);
            roomDTO.setHotelID(hotelID);

            // Handle image uploads
            if (image1 != null && !image1.isEmpty()) {
                String image1Name = saveImage(image1);
                roomDTO.setImage1(image1Name);
            }
            if (image2 != null && !image2.isEmpty()) {
                String image2Name = saveImage(image2);
                roomDTO.setImage2(image2Name);
            }
            if (image3 != null && !image3.isEmpty()) {
                String image3Name = saveImage(image3);
                roomDTO.setImage3(image3Name);
            }

            return ResponseEntity.ok(roomService.createRoom(roomDTO));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save images: " + e.getMessage());
        }
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
    public ResponseEntity<RoomDTO> updateRoom(
            @PathVariable Long id,
            @RequestParam("roomType") String roomType,
            @RequestParam("price") double price,
            @RequestParam("available") String available,
            @RequestParam("roomNumber") Long roomNumber,
            @RequestParam("hotelID") Long hotelID,
            @RequestParam(value = "image1", required = false) MultipartFile image1,
            @RequestParam(value = "image2", required = false) MultipartFile image2,
            @RequestParam(value = "image3", required = false) MultipartFile image3) {

        try {
            // Get existing room data
            RoomDTO existingRoom = roomService.getRoomById(id);

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(id);
            roomDTO.setRoomType(roomType);
            roomDTO.setPrice(price);
            roomDTO.setAvailable(available);
            roomDTO.setRoomNumber(roomNumber);
            roomDTO.setHotelID(hotelID);

            // Handle image uploads - keep existing images if no new ones uploaded
            if (image1 != null && !image1.isEmpty()) {
                String image1Name = saveImage(image1);
                roomDTO.setImage1(image1Name);
            } else {
                roomDTO.setImage1(existingRoom.getImage1());
            }

            if (image2 != null && !image2.isEmpty()) {
                String image2Name = saveImage(image2);
                roomDTO.setImage2(image2Name);
            } else {
                roomDTO.setImage2(existingRoom.getImage2());
            }

            if (image3 != null && !image3.isEmpty()) {
                String image3Name = saveImage(image3);
                roomDTO.setImage3(image3Name);
            } else {
                roomDTO.setImage3(existingRoom.getImage3());
            }

            return ResponseEntity.ok(roomService.updateRoom(id, roomDTO));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save images: " + e.getMessage());
        }
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

    private String saveImage(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }
}