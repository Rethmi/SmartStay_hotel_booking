package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.PaymentDTO;
import lk.ijse.gdse72.backend.entity.Booking;
import lk.ijse.gdse72.backend.entity.Payment;
import lk.ijse.gdse72.backend.repository.BookingRepository;
import lk.ijse.gdse72.backend.repository.PaymentRepository;
import lk.ijse.gdse72.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${payhere.merchant-id}")
    private String merchantId;

    @Value("${payhere.merchant-secret}")
    private String merchantSecret;

    @Value("${payhere.base-url}")
    private String payhereBaseUrl;

    @Value("${app.base-url}")
    private String appBaseUrl;

    private final PaymentRepository paymentRepository;

    private final BookingRepository bookingRepository;

    private final EmailServiceImpl emailService;

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
        payment.setPaymentMethod("CARD");
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setStatus(paymentDTO.getStatus());

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
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
//
//        // ===== Create payment if it doesn't exist =====
//        Payment payment = paymentRepository.findByBooking_Id(bookingId).orElse(null);
//        if (payment == null) {
//            payment = new Payment();
//            payment.setBooking(booking);
//            payment.setAmount(BigDecimal.valueOf(booking.getRoom().getPrice()));
//            payment.setPaymentDate(LocalDateTime.now());
//            payment.setStatus("PENDING"); // Not completed yet
//            paymentRepository.save(payment);
//        }
//
//        String amount = String.format("%.2f", booking.getRoom().getPrice());
//        String currency = "LKR";
//        String orderId = booking.getId().toString();
//        String hash = generatePayHereHash(merchantId, orderId, amount, currency, merchantSecret);
//
//        Map<String, Object> data = new LinkedHashMap<>();
//        data.put("merchant_id", merchantId);
//        data.put("return_url", appBaseUrl + "/payment-success.html");
//        data.put("cancel_url", appBaseUrl + "/payment-cancel.html");
//        data.put("notify_url", appBaseUrl + "/api/v1/payments/notify"); // Use your backend notify endpoint
//        data.put("order_id", orderId);
//        data.put("items", "Room Booking - Room " + booking.getRoom().getRoomNumber());
//        data.put("amount", amount);
//        data.put("currency", currency);
//        data.put("hash", hash);
//
//        String firstName = "Customer";
//        String lastName = "Name";
//        if (booking.getUser() != null && booking.getUser().getUsername() != null) {
//            String[] parts = booking.getUser().getUsername().split(" ");
//            firstName = parts[0];
//            lastName = parts.length > 1 ? parts[1] : "Name";
//        }
//
//        data.put("first_name", firstName);
//        data.put("last_name", lastName);
//        data.put("email", booking.getEmail());
//        data.put("phone", booking.getPhoneNumber());
//        data.put("address", booking.getCity() != null ? booking.getCity() : "Colombo");
//        data.put("city", booking.getCity() != null ? booking.getCity() : "Colombo");
//        data.put("country", "Sri Lanka");
//        data.put("sandbox", "1");
//
//        // Save payment ID in custom fields so you can track it later
//        data.put("custom_1", payment.getPaymentId().toString());
//        data.put("custom_2", bookingId.toString());
//
//        return data;
//    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public Map<String, Object> createPayHereFormData(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // ===== Create payment if it doesn't exist =====
        Payment payment = paymentRepository.findByBooking_Id(bookingId).orElse(null);
        if (payment == null) {
            payment = new Payment();
            payment.setBooking(booking);
            payment.setPaymentMethod("CARD");
            payment.setTransactionId(generateTransactionId());
            payment.setAmount(BigDecimal.valueOf(booking.getRoom().getPrice()));
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus("PENDING"); // Not completed yet
            paymentRepository.save(payment);

            // ==== Send booking confirmation email ====
            String bookingDetails =
                    "<p><b>Booking ID:</b> " + booking.getId() + "</p>" +
                            "<p><b>Room Number:</b> " + booking.getRoom().getRoomNumber() + "</p>" +
                            "<p><b>Price:</b> LKR " + booking.getRoom().getPrice() + "</p>" +
                            "<p><b>Customer:</b> " + booking.getUser().getUsername() + "</p>";

            // Payment link (optional: redirect to your frontend payment page)
            String paymentLink = appBaseUrl + "/pay.html?bookingId=" + bookingId;

            emailService.sendBookingConfirmationEmail(
                    booking.getEmail(),
                    bookingDetails,
//                    paymentLink
                    "Successfully payment"
            );
        }

        String amount = String.format("%.2f", booking.getRoom().getPrice());
        String currency = "LKR";
        String orderId = booking.getId().toString();
        String hash = generatePayHereHash(merchantId, orderId, amount, currency, merchantSecret);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("merchant_id", merchantId);
        data.put("return_url", appBaseUrl + "/payment-success.html");
        data.put("cancel_url", appBaseUrl + "/payment-cancel.html");
        data.put("notify_url", appBaseUrl + "/api/v1/payments/notify");
        data.put("order_id", orderId);
        data.put("items", "Room Booking - Room " + booking.getRoom().getRoomNumber());
        data.put("amount", amount);
        data.put("currency", currency);
        data.put("hash", hash);

        String firstName = "Customer";
        String lastName = "Name";
        if (booking.getUser() != null && booking.getUser().getUsername() != null) {
            String[] parts = booking.getUser().getUsername().split(" ");
            firstName = parts[0];
            lastName = parts.length > 1 ? parts[1] : "Name";
        }

        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("email", booking.getEmail());
        data.put("phone", booking.getPhoneNumber());
        data.put("address", booking.getCity() != null ? booking.getCity() : "Colombo");
        data.put("city", booking.getCity() != null ? booking.getCity() : "Colombo");
        data.put("country", "Sri Lanka");
        data.put("sandbox", "1");

        // Save payment ID in custom fields so you can track it later
        data.put("custom_1", payment.getPaymentId().toString());
        data.put("custom_2", bookingId.toString());

        return data;
    }


    @Override
    public void updatePaymentFromPayHere(Map<String,String> params) {
        Long bookingId = Long.parseLong(params.get("order_id"));
        String status = params.get("status");
        String transactionId = params.get("payment_id");

        Payment payment = paymentRepository.findByBooking_Id(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        payment.setStatus(status.toUpperCase());
        payment.setTransactionId(transactionId);
        paymentRepository.save(payment);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if("APPROVED".equalsIgnoreCase(status)) booking.setStatus(Booking.BookingStatus.CONFIRMED);
        else if("CANCELLED".equalsIgnoreCase(status) || "FAILED".equalsIgnoreCase(status)) booking.setStatus(Booking.BookingStatus.CANCELLED);

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



    private String generatePayHereHash(String merchantId, String orderId, String amount, String currency, String merchantSecret){
        try {
            String secretMd5 = md5Hex(merchantSecret).toUpperCase();
            return md5Hex(merchantId + orderId + amount + currency + secretMd5).toUpperCase();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error generating hash");
        }
    }


    private String md5Hex(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for(byte b: digest) sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}
