package lk.ijse.gdse72.backend.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AuthDTO {
    private String email;
    private String password;
}