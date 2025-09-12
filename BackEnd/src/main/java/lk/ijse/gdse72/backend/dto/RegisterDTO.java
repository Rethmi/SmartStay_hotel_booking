package lk.ijse.gdse72.backend.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDTO {

    private String username;
    private String password;
    private String email;
    private String role;//USER or ADMIN

}