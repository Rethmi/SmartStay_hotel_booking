package lk.ijse.gdse72.backend.controller;



import lk.ijse.gdse72.backend.dto.PaymentDTO;
import lk.ijse.gdse72.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.status(201).body(paymentService.createPayment(paymentDTO));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentDTO> getPaymentByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("/update-status/{paymentId}")
    public ResponseEntity<PaymentDTO> updatePaymentStatus(@PathVariable Long paymentId,
                                                           @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }

    // PayHere endpoints
//    @PostMapping("/create-payhere/{bookingId}")
//    public ResponseEntity<Map<String, Object>> createPayHereForm(@PathVariable Long bookingId) {
//        return ResponseEntity.ok(paymentService.createPayHereFormData(bookingId));
//    }
    @PostMapping("/create-payhere/{bookingId}")
    public ResponseEntity<?> createPayHereForm(@PathVariable Long bookingId) {
        try {
            Map<String, Object> formData = paymentService.createPayHereFormData(bookingId);
            return ResponseEntity.ok(formData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create payment form", "message", e.getMessage()));
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<String> handlePayHereNotification(@RequestParam Map<String, String> params) {
        paymentService.updatePaymentFromPayHere(params);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/test-hash/{bookingId}")
    public ResponseEntity<Map<String, String>> testHashGeneration(@PathVariable Long bookingId) {
        try {
            Map<String, Object> formData = paymentService.createPayHereFormData(bookingId);
            Map<String, String> debugInfo = new HashMap<>();

            debugInfo.put("merchant_id", formData.get("merchant_id").toString());
            debugInfo.put("order_id", formData.get("order_id").toString());
            debugInfo.put("amount", formData.get("amount").toString());
            debugInfo.put("currency", formData.get("currency").toString());
            debugInfo.put("hash", formData.get("hash").toString());
            debugInfo.put("hash_length", String.valueOf(formData.get("hash").toString().length()));

            return ResponseEntity.ok(debugInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}