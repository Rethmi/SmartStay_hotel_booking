package lk.ijse.gdse72.backend.dto;

import jakarta.persistence.*;
import lk.ijse.gdse72.backend.entity.Role;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;  // DB walata save wenna

    private String email;

    @Enumerated(EnumType.STRING)

    private Role role;

    private String password;
}