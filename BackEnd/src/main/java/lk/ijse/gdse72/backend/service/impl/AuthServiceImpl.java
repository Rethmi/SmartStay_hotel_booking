package lk.ijse.gdse72.backend.service.impl;


import lk.ijse.gdse72.backend.dto.AuthDTO;
import lk.ijse.gdse72.backend.dto.AuthResponseDto;
import lk.ijse.gdse72.backend.dto.RegisterDto;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.exception.UserAlreadyExistsException;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.AuthService;
import lk.ijse.gdse72.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDto authentication(AuthDTO authDto){
        User user = userRepository.findByEmail(authDto.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User email not Found"));

        if (!passwordEncoder.matches(authDto.getPassword(),user.getPassword())){
            throw new BadCredentialsException("Password incorrect");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDto(token,user.getRole(),user.getId().toString());
    }

    public String register(RegisterDto registerDto){
        if (userRepository.findByEmail(
                registerDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(
                        registerDto.getPassword()
                ))
                .email(registerDto.getEmail())
                .role(registerDto.getRole())
                .ProfileImage("default.jpg") // give a default image
                .build();
        userRepository.save(user);
        return "User registration Success";
    }

    @Autowired
    private JavaMailSender mailSender; // For sending OTP via email

    private Map<String, String> otpStorage = new HashMap<>(); // Temporary storage (Use Redis for production)

    public String generateOTP(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow();
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        String otp = String.valueOf(new Random().nextInt(999999 - 100000) + 100000); // Generate 6-digit OTP
        otpStorage.put(email, otp); // Store OTP temporarily

        sendEmail(email, otp); // Send OTP via email

        return "OTP sent successfully!";
    }

    public void sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }
    public boolean verifyOTP(String email, String otp) {
        if (!otpStorage.containsKey(email)) {
            throw new RuntimeException("OTP expired or invalid!");
        }

        if (otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email); // OTP is used, remove it
            return true;
        } else {
            throw new RuntimeException("Incorrect OTP!");
        }
    }
    public String resetPassword(String email, String newPassword) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow();
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password reset successfully!";
    }

}