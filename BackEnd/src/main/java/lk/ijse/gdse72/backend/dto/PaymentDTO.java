package lk.ijse.gdse72.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {


    private Long paymentId;
    private Long bookingId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // CREDIT_CARD, PAYPAL, BANK_TRANSFER
    private String transactionId;
    private String status;



}

