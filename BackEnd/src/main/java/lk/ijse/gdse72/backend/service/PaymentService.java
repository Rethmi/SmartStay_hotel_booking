package lk.ijse.gdse72.backend.service;


import lk.ijse.gdse72.backend.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO getPaymentById(Long paymentId);
    PaymentDTO getPaymentByBookingId(Long bookingId);
    List<PaymentDTO> getAllPayments();
    PaymentDTO updatePaymentStatus(Long paymentId, String status);

    // PayHere integration
    Map<String, Object> createPayHereFormData(Long bookingId);
    void updatePaymentFromPayHere(Map<String, String> params);
}