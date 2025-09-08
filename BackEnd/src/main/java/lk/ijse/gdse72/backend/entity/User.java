package lk.ijse.gdse72.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Explicitly map to user_id column
    private Long id;

    private String username;
    private String email;
    private String role;
    private String password;
    private String profileImage;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_hotel",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id")
    )
    private Set<Hotel> bookedHotels = new HashSet<>();
}