package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.AuthDTO;
import lk.ijse.gdse72.backend.dto.AuthResponseDto;
import lk.ijse.gdse72.backend.dto.RegisterDto;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.exception.UserAlreadyExistsException;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    String generateOTP(String email) ;
    void sendEmail(String email, String otp);
    public AuthResponseDto authentication(AuthDTO authDto);
    public String register(RegisterDto registerDto);

}