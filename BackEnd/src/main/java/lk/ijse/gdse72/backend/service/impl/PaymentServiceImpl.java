package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.PaymentDTO;
import lk.ijse.gdse72.backend.entity.Booking;
import lk.ijse.gdse72.backend.entity.Payment;
import lk.ijse.gdse72.backend.repository.BookingRepository;
import lk.ijse.gdse72.backend.repository.PaymentRepository;
import lk.ijse.gdse72.backend.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payhere.merchant-id}")
    private String merchantId;

    @Value("${payhere.merchant-secret}")
    private String merchantSecret;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Booking not found with ID: " + paymentDTO.getBookingId()));

        if (paymentRepository.findByBooking_Id(paymentDTO.getBookingId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Payment already exists for booking ID: " + paymentDTO.getBookingId());
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setStatus(paymentDTO.getStatus());

        // Update booking status using enum
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return convertToDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found with ID: " + paymentId));
        return convertToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBooking_Id(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found for booking ID: " + bookingId));
        return convertToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found with ID: " + paymentId));

        payment.setStatus(status);
        return convertToDTO(paymentRepository.save(payment));
    }

//    @Override
//    public Map<String, Object> createPayHereFormData(Long bookingId) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        "Booking not found"));
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("merchant_id", merchantId); // Use from properties
//        data.put("return_url", "http://localhost:8080/payment-success");
//        data.put("cancel_url", "http://localhost:8080/payment-cancel");
//        data.put("notify_url", "http://localhost:8080/api/v1/payments/notify");
//        data.put("order_id", booking.getId().toString());
//        data.put("items", "Room Booking - " + booking.getRoom().getRoomNumber());
//        data.put("amount", String.format("%.2f", booking.getRoom().getPrice())); // Format to 2 decimal places
//        data.put("currency", "LKR");
//        data.put("email", booking.getEmail());
////        data.put("first_name", booking.getUser().getFirstName());
////        data.put("last_name", booking.getUser().getLastName());
//        data.put("phone", booking.getPhoneNumber());
//        data.put("address", booking.getCity());
//        data.put("city", booking.getCity());
//        data.put("country", "Sri Lanka");
//        data.put("sandbox", "1");
//
//        // Generate hash
//        String hashString = generateHash(data, merchantSecret);
//        data.put("hash", hashString);
//
//        return data;
//    }

    @Override
    public Map<String, Object> createPayHereFormData(Long bookingId) {

        log.info("Creating PayHere form for booking ID: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {

                    log.error("Booking not found with ID: {}", bookingId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Booking not found");
                });

        String amount = String.format("%.2f", booking.getRoom().getPrice());
        String currency = "LKR";
        String orderId = booking.getId().toString();

        // Generate hash in the correct PayHere format
        // Format: merchant_id + order_id + amount + currency + MD5(merchant_secret)
        String merchantSecretMd5 = DigestUtils.md5DigestAsHex(merchantSecret.getBytes()).toUpperCase();
        String hashString = merchantId + orderId + amount + currency + merchantSecretMd5;
        String finalHash = DigestUtils.md5DigestAsHex(hashString.getBytes()).toUpperCase();

        log.debug("Hash components - merchantId: {}, orderId: {}, amount: {}, currency: {}, merchantSecretMd5: {}",
                merchantId, orderId, amount, currency, merchantSecretMd5);
        log.debug("Final hash: {}", finalHash);

        Map<String, Object> data = new LinkedHashMap<>();

        // Required fields
        data.put("merchant_id", merchantId);
        data.put("return_url", "http://localhost:3000/payment-success.html");
        data.put("cancel_url", "http://localhost:3000/payment-cancel.html");
        data.put("notify_url", "http://localhost:8080/api/v1/payments/notify");
        data.put("order_id", orderId);
        data.put("items", "Room Booking - Room " + booking.getRoom().getRoomNumber());
        data.put("amount", amount);
        data.put("currency", currency);
        data.put("hash", finalHash);

        // Customer information (optional but recommended)
        String firstName = "Customer";
        String lastName = "Name";

        if (booking.getUser() != null && booking.getUser().getUsername() != null) {
            String[] nameParts = booking.getUser().getUsername().split(" ");
            firstName = nameParts[0];
            lastName = nameParts.length > 1 ? nameParts[1] : "Name";
        }

        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("email", booking.getEmail());
        data.put("phone", booking.getPhoneNumber());
        data.put("address", booking.getCity() != null ? booking.getCity() : "Colombo");
        data.put("city", booking.getCity() != null ? booking.getCity() : "Colombo");
        data.put("country", "Sri Lanka");

        // Sandbox mode
        data.put("sandbox", "1");

        log.info("PayHere form data created successfully for booking ID: {}", bookingId);
        return data;
    }
    @Override
    public void updatePaymentFromPayHere(Map<String, String> params) {
        Long bookingId = Long.parseLong(params.get("order_id"));
        String status = params.get("status");
        String transactionId = params.get("payment_id");

        Payment payment = paymentRepository.findByBooking_Id(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found for booking ID: " + bookingId));

        payment.setStatus(status.toUpperCase());
        payment.setTransactionId(transactionId);
        paymentRepository.save(payment);

        // Update booking status based on payment status
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Booking not found"));

        if ("APPROVED".equalsIgnoreCase(status)) {
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
        } else if ("CANCELLED".equalsIgnoreCase(status) || "FAILED".equalsIgnoreCase(status)) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
        }
        bookingRepository.save(booking);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .status(payment.getStatus())
                .build();
    }

    private String generatePayHereHash(String merchantId, String orderId, String amount, String currency, String merchantSecret) {
        try {
            // PayHere expects: merchant_id + order_id + amount + currency + MD5(merchant_secret)
            String merchantSecretMd5 = DigestUtils.md5DigestAsHex(merchantSecret.getBytes()).toUpperCase();
            String concatenatedString = merchantId + orderId + amount + currency + merchantSecretMd5;

            // Generate MD5 hash of the concatenated string
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(concatenatedString.getBytes("UTF-8"));

            // Convert to hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating hash");
        }
    }
}