package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.dto.*;
import lk.ijse.gdse72.backend.service.AuthService;
import lk.ijse.gdse72.backend.service.impl.AuthServiceImpl;
import lk.ijse.gdse72.backend.service.impl.UserServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<APIResponse> registerUser(@RequestBody RegisterDto registerDto) {
        System.out.println("Registering user: " + registerDto.getUsername() + " with email: " + registerDto.getEmail());
        String message = authService.register(registerDto);
        return ResponseEntity.ok(new APIResponse(200, "OK", message));
    }


    @PostMapping("/login")
    public ResponseEntity<APIResponse> logInUser(@RequestBody AuthDTO authDto){
        return ResponseEntity.ok(new APIResponse(
                200,
                "OK",
                authService.authentication(authDto)
        ));
    }
//
//    private final JwtUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
//    private final UserServiceImpl userService;
//    private final ResponseDTO responseDTO;
//    private final AuthServiceImpl authServiceImpl;
//
//    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, ResponseDTO responseDTO, AuthServiceImpl authServiceImpl) {
//        this.jwtUtil = jwtUtil;
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.responseDTO = responseDTO;
//        this.authServiceImpl = authServiceImpl;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials", e.getMessage()));
//        }
//
//        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
//        if (loadedUser == null) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(new ResponseDTO(HttpStatus.CONFLICT.value(), "Authorization Failure! Please Try Again", null));
//        }
//
//        String token = jwtUtil.generateToken(loadedUser.getEmail());
//        if (token == null || token.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(new ResponseDTO(HttpStatus.CONFLICT.value(), "Authorization Failure! Please Try Again", null));
//        }
//
//        AuthDTO authDTO = new AuthDTO();
//        authDTO.setEmail(loadedUser.getEmail());
//        authDTO.setToken(token);
//        authDTO.setRole(loadedUser.getRole());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ResponseDTO(HttpStatus.CREATED.value(), "Success", authDTO));
//    }
//
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        String response = authServiceImpl.generateOTP(request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(HttpStatus.CREATED.value(), "Success", response));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPVerificationDTO request) {
        boolean isValid = authServiceImpl.verifyOTP(request.getEmail(), request.getOtp());
        return isValid ? ResponseEntity.ok("OTP Verified! Reset your password.")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO request) {
        String response = authServiceImpl.resetPassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok(response);
    }
}