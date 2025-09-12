//package lk.ijse.gdse72.backend.controller;
//
//import lk.ijse.gdse72.backend.dto.*;
//import lk.ijse.gdse72.backend.service.AuthService;
//import lk.ijse.gdse72.backend.service.impl.AuthServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
//@RequestMapping("api/v1/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final AuthService authService;
//    private final AuthServiceImpl authServiceImpl;
//
//    @PostMapping("/register")
//    public ResponseEntity<APIResponse> registerUser(@RequestBody RegisterDto registerDto) {
//        System.out.println("Registering user: " + registerDto.getUsername() + " with email: " + registerDto.getEmail());
//        String message = authService.register(registerDto);
//        return ResponseEntity.ok(new APIResponse(200, "OK", message));
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<APIResponse> logInUser(@RequestBody AuthDTO authDto) {
//        return ResponseEntity.ok(new APIResponse(
//                200,
//                "OK",
//                authService.authentication(authDto)
//        ));
//    }
//}

package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.APIResponse;
import lk.ijse.gdse72.backend.dto.AuthDTO;
import lk.ijse.gdse72.backend.dto.RegisterDTO;
import lk.ijse.gdse72.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(
            @RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                authService.register(registerDTO)));
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(
            @RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                authService.authenticate(authDTO)));
    }
}