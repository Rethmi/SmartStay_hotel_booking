package lk.ijse.gdse72.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
    private String amenities;
    private String phoneNumber;
    private String image;

    @ManyToMany(mappedBy = "bookedHotels", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
