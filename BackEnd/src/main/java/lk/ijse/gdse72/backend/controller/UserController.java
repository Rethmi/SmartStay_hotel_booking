// UserController.java
package lk.ijse.gdse72.backend.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse72.backend.dto.AuthDTO;
import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.dto.UserDTO;
import lk.ijse.gdse72.backend.service.OtpService;
import lk.ijse.gdse72.backend.service.UserService;
import lk.ijse.gdse72.backend.service.impl.EmailServiceImpl;
import lk.ijse.gdse72.backend.service.impl.OtpServiceImpl;
import lk.ijse.gdse72.backend.service.impl.UserServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;
    private final OtpServiceImpl otpServiceImpl;
    private final EmailServiceImpl emailServiceImpl;

    public UserController(UserService userService, JwtUtil jwtUtil, UserServiceImpl userServiceImpl, OtpService otpService, OtpServiceImpl otpServiceImpl, EmailServiceImpl emailServiceImpl) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userServiceImpl = userServiceImpl;
        this.otpServiceImpl = otpServiceImpl;
        this.emailServiceImpl = emailServiceImpl;
    }

//    @PostMapping("/register/{otp}")
//    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO,
//                                                    @PathVariable("otp") String otp) {
//        System.out.println("Email: " + userDTO.getEmail());
//        System.out.println("OTP: " + otp);
//        if (otp==null || otp.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "OTP is required!", null));
//        }
//        try {
//            if (otp != null) {
//                System.out.println("OTP: " + otp);
//                if (!otpServiceImpl.verifyOtp(userDTO.getEmail(), otp)) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                            .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid OTP!", null));
//                }
//                otpServiceImpl.clearOtp(userDTO.getEmail());
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid OTP!", null));
//            }
//
//            int res = userService.saveUser(userDTO);
//            switch (res) {
//                case 201 -> { // HttpStatus.CREATED.value()
//                    String token = jwtUtil.generateToken(userDTO);
//                    AuthDTO authDTO = new AuthDTO(userDTO.getEmail(),token);
//                    return ResponseEntity.status(HttpStatus.CREATED)
//                            .body(new ResponseDTO(HttpStatus.CREATED.value(), "User registered successfully", authDTO));
//                }
//                case 406 -> { // HttpStatus.NOT_ACCEPTABLE.value()
//                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                            .body(new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), "Email already in use", null));
//                }
//                default -> {
//                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                            .body(new ResponseDTO(HttpStatus.BAD_GATEWAY.value(), "Registration failed", null));
//                }
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: " + e.getMessage(), null));
//        }
//    }
@PostMapping("/register/{otp}")
public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO,
                                                @PathVariable("otp") String otp) {
    if (userDTO == null || userDTO.getEmail() == null || otp == null || otp.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Email and OTP are required!", null));
    }

    try {
        // Verify OTP
        boolean isOtpValid = otpServiceImpl.verifyOtp(userDTO.getEmail(), otp);
        if (!isOtpValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Invalid OTP!", null));
        }

        otpServiceImpl.clearOtp(userDTO.getEmail());

        // Save user
        int res = userService.saveUser(userDTO);

        if (res == 201) {
            String token = jwtUtil.generateToken(userDTO.getEmail());
            AuthDTO authDTO = new AuthDTO(userDTO.getEmail(), token);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(HttpStatus.CREATED.value(), "User registered successfully", authDTO));
        } else if (res == 409) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(HttpStatus.CONFLICT.value(), "Email already in use", null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Registration failed", null));
        }

    } catch (Exception e) {
        e.printStackTrace(); // console එකේ full error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage(), null));
    }
}


    @PostMapping("/send-otp")
    public ResponseEntity<ResponseDTO> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Email: " + email);
        if (userServiceImpl.allReadyUsedEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Email is already registered", null));
        }
        String otp = otpServiceImpl.generateOtp(email);
        emailServiceImpl.sendOtpEmail(email, otp);
        return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "OTP sent successfully to " + email, null));
    }

    @DeleteMapping(value = "/delete/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity <ResponseDTO> deleteUser(@PathVariable String email,@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        userServiceImpl.deleteUser(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", null));
    }

    @PutMapping("/update/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateUserRole(@PathVariable String email,@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        String newRole = request.get("role");

        if (newRole == null || newRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Role cannot be empty", null));
        }

        userService.updateUserRole(email, newRole);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "User role updated successfully", null));
    }
//
//    @GetMapping("/getAll")
//    @PreAuthorize("hasAnyAuthority('ADMIN','Manager','USER')")
//    public ResponseEntity<ResponseDTO> getAllUsers(@RequestHeader("Authorization") String token) {
//        String jwt = token.substring(7);
//////        String username = jwtUtil.getUsernameFromToken(jwt);
////        if (username == null) {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND)
////                    .body(new ResponseDTO(HttpStatus.NOT_FOUND.value(), "Invalid Token", null));
////        }
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", userService.getAll()));
//    }
@GetMapping("/getAll")
@PreAuthorize("hasAnyAuthority('ADMIN','Manager','USER')")
public ResponseEntity<ResponseDTO> getAllUsers(@RequestHeader("Authorization") String token) {
    try {
        String jwt = token.substring(7);
        // Verify token is valid (optional additional validation)
        if (!jwtUtil.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Invalid Token", null));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", userService.getAll()));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching users", null));
    }
}

    @GetMapping("get/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email,@RequestHeader("Authorization") String token) {
        System.out.println("Email: " + email);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", userService.getUserByEmail(email)));
    }

    @GetMapping("getName/{Id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getUserNameById(@PathVariable Long Id,@RequestHeader("Authorization") String token) {
        System.out.println("Id: " + Id);
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", userServiceImpl.getUserNameById(Id)));
    }
}
