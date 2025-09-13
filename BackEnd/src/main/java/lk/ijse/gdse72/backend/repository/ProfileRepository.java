package lk.ijse.gdse72.backend.repository;


import lk.ijse.gdse72.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}