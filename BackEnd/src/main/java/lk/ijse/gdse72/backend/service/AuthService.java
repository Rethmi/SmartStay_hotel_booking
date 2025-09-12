package lk.ijse.gdse72.backend.service;




import lk.ijse.gdse72.backend.dto.AuthDTO;
import lk.ijse.gdse72.backend.dto.AuthResponseDTO;
import lk.ijse.gdse72.backend.dto.RegisterDTO;
import lk.ijse.gdse72.backend.entity.Role;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user =
                userRepository.findByEmail(authDTO.getEmail())
                        .orElseThrow(
                                ()-> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches
                (authDTO.getPassword(),
                        user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        String  token = jwtUtil.generateToken(authDTO.getEmail());
        String role = String.valueOf(user.getRole());
        return new AuthResponseDTO(token,role);
    }
    public String register(RegisterDTO registerDTO) {

        // Check if email already exists
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

//        // Password match validation
//        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
//            throw new RuntimeException("Passwords do not match");
//        }

        // Map DTO to Entity
        User user = User.builder()
                .username(registerDTO.getUsername())  // fix here

                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .email(registerDTO.getEmail())
                .role(String.valueOf(Role.valueOf(registerDTO.getRole())))
                .build();

        userRepository.save(user);
        return "User Registration Successful";
    }

}
