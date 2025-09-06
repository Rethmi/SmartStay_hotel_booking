//package lk.ijse.gdse72.backend.repository;
//
//import org.example.smarthotelbookingwebsite.entity.Payment;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    @Query("SELECT p FROM Payment p JOIN p.booking b JOIN b.room r WHERE r.hotel.id = :hotelId")
//    List<Payment> findPaymentsByHotelId(@Param("hotelId") Long hotelId);
//}
