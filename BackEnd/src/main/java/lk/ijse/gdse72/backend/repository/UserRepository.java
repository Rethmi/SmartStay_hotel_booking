package lk.ijse.gdse72.backend.repository;

import lk.ijse.gdse72.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findIdByEmailADD(@Param("email") String email);
}